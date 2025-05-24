export interface Movie {
    id: number;
    title: string;
    genre: string;
    releaseDate: string;
    status: 'Highlight' | 'Now Showing' | 'Coming Soon' | 'Stopped Showing';
}