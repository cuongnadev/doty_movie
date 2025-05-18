import { Controller, Get, Post, Body, Patch, Param, Delete, Query } from '@nestjs/common';
import { TicketService } from './ticket.service';
import { CreateTicketDto } from './dto/create-ticket.dto';
import { UpdateTicketDto } from './dto/update-ticket.dto';

@Controller('ticket')
export class TicketController {
  constructor(private readonly ticketService: TicketService) {}

  @Post("create")
  create(@Body() createTicketDto: CreateTicketDto) {
    return this.ticketService.create(createTicketDto);
  }

  @Post("payment/webhook")
  async handlePaymentWebhook(@Body() payload: any) {
     console.log("Received webhook:", payload);
    return this.ticketService.handlePaymentWebhook(payload);
  }

  @Get("my-ticket")
  findTicketsByUser(@Query("userId") userId: number) {
    return this.ticketService.findTicketsByUser(userId);
  }

  @Get('status/:ticketCode')
  getStatus(@Param('ticketCode') ticketCode: string) {
    return this.ticketService.getStatus(ticketCode);
  }

  @Get()
  findAll() {
    return this.ticketService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.ticketService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateTicketDto: UpdateTicketDto) {
    return this.ticketService.update(+id, updateTicketDto);
  }

  @Patch('cancel/:ticketCode')
  cancelTicket(@Param('ticketCode') ticketCode: string) {
    return this.ticketService.cancelTicket(ticketCode);
  }

  @Delete('delete/:ticketCode')
  remove(@Param('ticketCode') ticketCode: string) {
    return this.ticketService.remove(ticketCode);
  }
}
