import { BadRequestException, Injectable, UnauthorizedException } from '@nestjs/common';
import { UserService } from '../user/user.service';
import { JwtService } from '@nestjs/jwt';
import { User } from '../user/entities/user.entity';
import * as bcrypt from 'bcrypt';
import { RegisterDTO } from './dto/register.dto';
import { LoginResponseDTO } from './dto/login-response.dto';
import { MailerService } from "@nestjs-modules/mailer"
import { CodeStoreService } from './code-store.service';

@Injectable()
export class AuthService {
    constructor(
        private userService: UserService,
        private jwtService: JwtService,
        private mailerService: MailerService,
        private codeStoreService: CodeStoreService
    ) {}

    async login(user: User): Promise<LoginResponseDTO> {
        const payload = { email: user.email, sub: user.id };
        return {
            access_token: this.jwtService.sign(payload),
            email: user.email,
        };
    }

    async register(email: string) {
        const user = await this.userService.findByEmail(email);
        if(user) throw new BadRequestException("Email already registered");

        const code = this.codeStoreService.generateCode(email);

        await this.mailerService.sendMail({
            to: email,
            subject: "Verifications Code",
            text: `Your code is: ${code}. It is valid for 5 minute.`
        });

        return { message: 'Verification code sent to email' };
    }

    async verify(userData: RegisterDTO, code: string) {
        const isValid = this.codeStoreService.verifyCode(userData.email, code);
        if(!isValid) throw new BadRequestException("Invalid or expires code.");

        const hassPass = await bcrypt.hash(userData.password, 10);
        const newUser = await this.userService.create({ email: userData.email, password: hassPass, name: userData.name });

        return { message: "User registed successfully!", newUser }
    }

    async validateUser(email: string, password: string): Promise<User> {
        const user = await this.userService.findByEmail(email);
        if(user && await bcrypt.compare(password, user.password)) {
            return user
        }
        throw new UnauthorizedException('Invalid credentials');
    }
}
