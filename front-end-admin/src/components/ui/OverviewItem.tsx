const OverviewItem = ({ label, quantity }: { label: string, quantity: number }) => {
    return (
        <div className="p-4 bg-white border rounded-xl shadow text-center">
            <p className="text-gray-500">{ label }</p>
            <p className="text-3xl font-bold text-[#6C47DB]">{ quantity }</p>
        </div>
    );
}

export default OverviewItem;