// pages/Movie.tsx
import React, { useState } from 'react';
import { MovieItem } from '../components';
import type { Movie } from '../types/movie';

const initialMovies: Movie[] = [
    {
        id: 1,
        title: 'Avengers: Endgame',
        genre: 'Action',
        releaseDate: '2019-04-26',
        status: 'Now Showing',  // Đang chiếu
    },
    {
        id: 2,
        title: 'Frozen 2',
        genre: 'Animation',
        releaseDate: '2019-11-22',
        status: 'Stopped Showing', // Ngừng chiếu
    },
    {
        id: 3,
        title: 'The Batman',
        genre: 'Action',
        releaseDate: '2022-03-04',
        status: 'Coming Soon',  // Sắp chiếu
    },
    {
        id: 4,
        title: 'The Marvels',
        genre: 'Superhero',
        releaseDate: '2023-11-10',
        status: 'Now Showing', // Đang chiếu
    },
    {
        id: 5,
        title: 'Spider-Man: No Way Home',
        genre: 'Superhero',
        releaseDate: '2021-12-17',
        status: 'Highlight',
    },
];

const Movie: React.FC = () => {
    const [movies, setMovies] = useState<Movie[]>(initialMovies);

    const handleDelete = (id: number) => {
        if (window.confirm('Bạn có chắc muốn xóa phim này?')) {
            setMovies((prev) => prev.filter((movie) => movie.id !== id));
        }
    };

    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold text-[#6C47DB] mb-6">Movie list</h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {movies.map((movie) => (
                    <MovieItem key={movie.id} movie={movie} onDelete={handleDelete} />
                ))}
            </div>
        </div>
    );
};

export default Movie;
