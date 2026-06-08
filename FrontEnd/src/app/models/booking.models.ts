export interface BarberService {
  id: string;
  name: string;
  duration: string;
  price: number;
  icon: 'scissors' | 'beard' | 'combo' | 'color' | 'hydration';
}

export interface Barber {
  id: string;
  name: string;
  specialty: string;
  initials: string;
}

export interface TimeSlot {
  time: string;
  available: boolean;
}

export interface BookingSelection {
  service: BarberService | null;
  barber: Barber | null;
  date: Date | null;
  time: string | null;
}

