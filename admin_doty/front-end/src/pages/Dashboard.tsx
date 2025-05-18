import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const data = [
    { month: 'Th1', revenue: 20000000 },
    { month: 'Th2', revenue: 35000000 },
    { month: 'Th3', revenue: 25000000 },
    { month: 'Th4', revenue: 40000000 },
    { month: 'Th5', revenue: 45000000 },
];

const Dashboard = () => {
    return (
        <div className="p-6 space-y-8">
            <h1 className="text-3xl font-bold text-[#6C47DB]">System overview</h1>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="p-4 bg-white border rounded-xl shadow text-center">
                    <p className="text-gray-500">Total movies</p>
                    <p className="text-3xl font-bold text-[#6C47DB]">24</p>
                </div>
                <div className="p-4 bg-white border rounded-xl shadow text-center">
                    <p className="text-gray-500">Total ticket sold</p>
                    <p className="text-3xl font-bold text-[#6C47DB]">456</p>
                </div>
                <div className="p-4 bg-white border rounded-xl shadow text-center">
                    <p className="text-gray-500">Revenue (VNĐ)</p>
                    <p className="text-3xl font-bold text-[#6C47DB]">108,500,000 ₫</p>
                </div>
            </div>

            <div className="bg-white p-4 rounded-xl shadow">
                <h2 className="text-xl font-semibold mb-4 text-[#6C47DB]">Monthly Revenue Chart</h2>
                <ResponsiveContainer width="100%" height={300}>
                    <BarChart data={data} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
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
