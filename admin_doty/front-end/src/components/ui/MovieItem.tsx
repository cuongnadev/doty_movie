import React from 'react';
import type { Movie } from '../../types/movie';

interface MovieItemProps {
    movie: Movie;
    onDelete: (id: number) => void;
}

const MovieItem: React.FC<MovieItemProps> = ({ movie, onDelete }) => {
    const statusColor: Record<Movie['status'], string> = {
        'Highlight': 'bg-purple-100 text-purple-700',
        'Now Showing': 'bg-green-100 text-green-700',
        'Coming Soon': 'bg-yellow-100 text-yellow-700',
        'Stopped Showing': 'bg-red-100 text-red-700',
    };

    return (
        <div className="bg-white rounded-xl shadow p-4 flex flex-col justify-between gap-4 border border-[#6C47DB]">
            <div>
                <h2 className="text-xl font-semibold text-[#6C47DB]">{movie.title}</h2>
                <p className="text-sm text-gray-600">Genre: {movie.genre}</p>
                <p className="text-sm text-gray-600">Release Date: {movie.releaseDate}</p>
                <span className={`inline-block mt-2 px-2 py-1 text-sm rounded-full font-medium ${statusColor[movie.status]}`}>
                    {movie.status}
                </span>
            </div>
            <div className="flex gap-2">
                <button className="px-3 py-1 text-sm bg-blue-500 text-white rounded hover:bg-blue-600">Update</button>
                <button
                    className="px-3 py-1 text-sm bg-red-500 text-white rounded hover:bg-red-600"
                    onClick={() => onDelete(movie.id)}
                >
                    Delete
                </button>
            </div>
        </div>
    );
};

export default MovieItem;