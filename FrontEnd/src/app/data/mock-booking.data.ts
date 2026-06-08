import { Barber, BarberService } from '../models/booking.models';

export const SERVICES: BarberService[] = [
  { id: 'corte', name: 'Corte', duration: '45 min', price: 55, icon: 'scissors' },
  { id: 'barba', name: 'Barba', duration: '30 min', price: 40, icon: 'beard' },
  { id: 'combo', name: 'Combo Corte + Barba', duration: '75 min', price: 85, icon: 'combo' },
  { id: 'platinado', name: 'Platinado', duration: '120 min', price: 150, icon: 'color' },
  { id: 'hidratacao', name: 'Hidratação', duration: '40 min', price: 45, icon: 'hydration' },
];

export const DISPLAY_SERVICES = SERVICES.slice(0, 4);

export const BARBERS: Barber[] = [
  { id: 'carlos', name: 'Carlos', specialty: 'Degradê', initials: 'C' },
  { id: 'rafael', name: 'Rafael', specialty: 'Navalhado', initials: 'R' },
  { id: 'diego', name: 'Diego', specialty: 'Coloração', initials: 'D' },
  { id: 'sem-preferencia', name: 'Sem preferência', specialty: 'Qualquer barbeiro disponível', initials: '' },
];
