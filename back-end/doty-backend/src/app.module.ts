import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { MovieModule } from './modules/movie/movie.module';
import { MediaModule } from './modules/media/media.module';
import { TheaterModule } from './modules/theater/theater.module';
import { ShowtimeModule } from './modules/showtime/showtime.module';
import { SeatModule } from './modules/seat/seat.module';
import { PaymentModule } from './modules/payment/payment.module';
import { UserModule } from './modules/user/user.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { configDotenv } from 'dotenv';
import { AuthModule } from './modules/auth/auth.module';
import databaseConfig from './config/database.config';
import { MailerModule } from "@nestjs-modules/mailer"
import { TicketModule } from './modules/ticket/ticket.module';
import { MovieFavoriteModule } from './modules/movie-favorite/movie-favorite.module';
configDotenv()

console.log(process.env.NODE_ENV);

@Module({
  imports: [
    MailerModule.forRoot({
      transport: {
        service: "gmail",
        auth: {
          user: "cuongna.dev@gmail.com",
          pass: "obnt cvgv vcby sxwz"
        },
      },
      defaults: {
        from: '"No Reply" <cuongna.dev@gmail.com>',
      }
    }),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: process.env.NODE_ENV === 'production' ? '.production.env' : '.local.env',
      load: [databaseConfig]
    }) ,
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => {
        const isProd = configService.get('NODE_ENV') === 'production';
        const dbConfig = configService.get('database');

        return isProd 
          ? {
            type: "postgres",
            url: configService.get('DATABASE_URL'),
            autoLoadEntities: true,
            synchronize: false,
            logging: false,
            extra: {
              ssl: { rejectUnauthorized: false },
              max: 10,
            },
          }
        : {
          type: "postgres",
          host: dbConfig.host,
          port: dbConfig.port,
          username: dbConfig.username,
          password: dbConfig.password,
          database: dbConfig.database,
          autoLoadEntities: true,
          synchronize: true,
          logging: true,
        }
      }
    }),
    MovieModule, MediaModule, TheaterModule, ShowtimeModule, SeatModule, PaymentModule, UserModule, AuthModule, TicketModule, MovieFavoriteModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
