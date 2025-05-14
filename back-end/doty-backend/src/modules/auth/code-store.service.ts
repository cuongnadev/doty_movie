import { Injectable } from "@nestjs/common";

@Injectable()
export class CodeStoreService {
    private codes = new Map<string, {code: string, expiresAt: number}>()

    generateCode(email: string): string {
        const code = Math.floor(100000 + Math.random() * 900000).toString();
        const expiresAt = Date.now() + 5 * 60_000;

        this.codes.set(email, { code, expiresAt });
        return code;
    }

    verifyCode(email: string, code: string): boolean {
        const data = this.codes.get(email);
        if(!data) return false;

        const isExpired = Date.now() > data.expiresAt;
        const isValid = data.code === code;

        if(!isExpired && isValid) {
            this.codes.delete(email);
            return true;
        }

        return false;
    }
}