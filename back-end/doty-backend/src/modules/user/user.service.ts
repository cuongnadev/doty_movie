import { BadRequestException, Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from './entities/user.entity';
import { Long, Repository } from 'typeorm';
import { RegisterDTO } from '../auth/dto/register.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { IsEmail } from 'class-validator';
import { ChangePasswordDto } from './dto/change-password.dto';
import * as bcrypt from 'bcrypt';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User) private userRepo: Repository<User>
  ) {}
  async findOne(userId: number): Promise<User | null> {
    return this.userRepo.findOne({ where: { id: userId } });
  }

  async findByEmail(email: string): Promise<User | null> {
    return this.userRepo.findOne({ where: { email } });
  }

  async create (userData: RegisterDTO): Promise<RegisterDTO> {
    const user = this.userRepo.create(userData);
    return this.userRepo.save(user);
  }

  async update(userId: number, updateUserDto: UpdateUserDto) {
    const { name, email } = updateUserDto;
    const user = await this.userRepo.findOne({ where: { id: userId } });

    if (!user) {
        throw new NotFoundException(`User with ID ${userId} not found`);
    }

    if (name !== undefined) {
        user.name = name;
    }

    if (email !== undefined) {
        user.email = email;
    }

    return await this.userRepo.save(user);
  }

  async changePassword(userId: number, changePasswordDto: ChangePasswordDto) {
    const { oldPassword, newPassword } = changePasswordDto;
    const user = await this.userRepo.findOne({ where: { id: userId } });

    if(!user) {
      throw new NotFoundException(`User with ID ${userId} not found`);
    }

    const isPasswordValid = await bcrypt.compare(oldPassword, user.password);
    
    if (!isPasswordValid) {
      throw new BadRequestException('Old password is incorrect');
    }

    user.password = await bcrypt.hash(newPassword, 10);
    return this.userRepo.save(user);
  }
}
