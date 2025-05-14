import { format } from "date-fns";

export function formatTime(date: Date) {
    return format(date, "MMMM dd, yyyy - HH:mm a")
}