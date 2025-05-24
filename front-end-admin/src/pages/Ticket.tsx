// pages/Ticket.tsx
import React, { useState } from 'react';
import { TicketItem } from '../components';
import type { Ticket } from '../types/ticket';

const initialTickets: Ticket[] = [
    {
        id: 1,
        movieTitle: 'Avengers: Endgame',
        customerName: 'Nguyễn Văn A',
        seatNumber: 'A10',
        price: 120000,
        time: '20:00 - 24/05/2025',
    },
    {
        id: 2,
        movieTitle: 'Frozen 2',
        customerName: 'Trần Thị B',
        seatNumber: 'B5',
        price: 90000,
        time: '18:00 - 23/05/2025',
    },
    {
        id: 3,
        movieTitle: 'The Batman',
        customerName: 'Lê Văn C',
        seatNumber: 'C3',
        price: 100000,
        time: '21:30 - 25/05/2025',
    },
];

const Ticket: React.FC = () => {
    const [tickets, setTickets] = useState<Ticket[]>(initialTickets);

    const handleCancel = (id: number) => {
        if (window.confirm('Bạn có chắc muốn hủy vé này?')) {
            setTickets((prev) => prev.filter((ticket) => ticket.id !== id));
        }
    };

    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold text-[#6C47DB] mb-6">Ticket list</h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {tickets.map((ticket) => (
                    <TicketItem key={ticket.id} ticket={ticket} onCancel={handleCancel} />
                ))}
            </div>
        </div>
    );
};

export default Ticket;
