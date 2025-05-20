import { Body, Controller, Get, Param, ParseIntPipe, Patch } from '@nestjs/common';
import { UserService } from './user.service';
import { UpdateUserDto } from './dto/update-user.dto';
import { ChangePasswordDto } from './dto/change-password.dto';

@Controller('user')
export class UserController {
    constructor(private readonly userService: UserService) {}

    @Get(":userId")
    async getUser(@Param("userId", ParseIntPipe) userId: number) {
        return this.userService.findOne(userId);
    }

    @Patch(":userId")
    async updateUser(@Param("userId", ParseIntPipe) userId: number, @Body() updateUserDto: UpdateUserDto) {
        return this.userService.update(userId, updateUserDto);
    }

    @Patch(":userId/password")
    async changePassword(@Param("userId", ParseIntPipe) userId: number, @Body() changePasswordDto: ChangePasswordDto) {
        return this.userService.changePassword(userId, changePasswordDto);
    }
}
