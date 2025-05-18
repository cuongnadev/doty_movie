import { registerAs } from '@nestjs/config';
import { configDotenv } from 'dotenv';
configDotenv();

function required(name: string): string {
  const value = process.env[name];
  if (!value) {
    throw new Error(`Missing environment variable: ${name}`);
  }
  return value;
}

export default registerAs('database', () => {
  const isProd = process.env.NODE_ENV === 'production';

  if (isProd) {
    return {
      url: required('DATABASE_URL'),
    };
  }

  return {
    host: required('DB_HOST'),
    port: parseInt(required('DB_PORT'), 10),
    username: required('DB_USERNAME'),
    password: required('DB_PASSWORD'),
    database: required('DB_NAME'),
  };
});
