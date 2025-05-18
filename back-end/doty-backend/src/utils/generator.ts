export function generateTicketCode(): string {
  const randomNumber = Math.floor(100000 + Math.random() * 900000);
  const timestamp = Date.now().toString().slice(-6);
  return `${randomNumber}${timestamp}`;
}