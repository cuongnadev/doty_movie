import { useQuery } from '@tanstack/react-query';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { fetchDashboardData } from '../api/dashboard-api';
import OverviewItem from '../components/ui/OverviewItem';

const Dashboard = () => {
    const { data, isLoading, isError, error } = useQuery({
        queryKey: ['dashboard'],
        queryFn: fetchDashboardData,
    });

    if (isError) {
        return (
        <div className="p-6 text-red-500 text-center">
            <p>Lỗi: {error?.message || 'Không thể tải dữ liệu dashboard'}</p>
            <button
            className="mt-4 px-4 py-2 bg-[#6C47DB] text-white rounded cursor-pointer"
            onClick={() => window.location.reload()}
            >
            Thử lại
            </button>
        </div>
        );
    }

    if (isLoading || !data) {
        return <div className="p-6 text-center text-[#6C47DB] text-center">Đang tải dữ liệu...</div>;
    }

    console.log(data);

    const { revenueData, overviewData } = data;
    return (
        <div className="p-6 space-y-8">
            <h1 className="text-3xl font-bold text-[#6C47DB]">System overview</h1>
        
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                {overviewData.map((data, index) => (
                    <OverviewItem label={data.label} quantity={data.value} key={index}/>
                ))}
            </div>

            <div className="bg-white p-4 rounded-xl shadow">
                <h2 className="text-xl font-semibold mb-4 text-[#6C47DB]">Monthly Revenue Chart</h2>
                <ResponsiveContainer width="100%" height={300}>
                    <BarChart data={revenueData} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="month" />
                        <YAxis tickFormatter={(value) => `${(value / 1_000_000)}tr`} />
                        <Tooltip formatter={(value) => `${value.toLocaleString()} ₫`} />
                        <Bar dataKey="revenue" fill="#6C47DB" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};

export default Dashboard;
