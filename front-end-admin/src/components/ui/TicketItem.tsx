// components/TicketItem.tsx
import React from 'react';
import type { Ticket } from '../../types/ticket';

interface TicketItemProps {
    ticket: Ticket;
    onCancel: (id: number) => void;
}

const TicketItem: React.FC<TicketItemProps> = ({ ticket, onCancel }) => {
    return (
        <div className="bg-white border border-[#6C47DB] rounded-xl shadow p-4 flex flex-col justify-between gap-3">
            <div>
                <h2 className="text-lg font-semibold text-[#6C47DB]">{ticket.movieTitle}</h2>
                <p className="text-sm text-gray-700">Customer: {ticket.customerName}</p>
                <p className="text-sm text-gray-700">Seats: {ticket.seatNumber}</p>
                <p className="text-sm text-gray-700">Price: {ticket.price.toLocaleString()} VNĐ</p>
                <p className="text-sm text-gray-700">Showtime: {ticket.time}</p>
            </div>
            <button
                className="mt-2 px-3 py-1 text-sm bg-red-500 text-white rounded hover:bg-red-600"
                onClick={() => onCancel(ticket.id)}
            >
                Cancel
            </button>
        </div>
    );
};

export default TicketItem;