import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, computed, signal } from '@angular/core';
import { BARBERS, SERVICES } from '../../data/mock-booking.data';
import { Barber, BarberService, TimeSlot } from '../../models/booking.models';

type BookingStep = 1 | 2 | 3 | 4;

interface CalendarDay {
  date: Date;
  label: number;
  inMonth: boolean;
  disabled: boolean;
}

@Component({
  selector: 'app-booking',
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css',
})
export class BookingComponent {
  protected readonly services = SERVICES;
  protected readonly barbers = BARBERS;
  protected readonly steps: { id: BookingStep; label: string }[] = [
    { id: 1, label: 'Serviço' },
    { id: 2, label: 'Barbeiro' },
    { id: 3, label: 'Data' },
    { id: 4, label: 'Horário' },
  ];
  protected readonly weekDays = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];

  protected readonly currentStep = signal<BookingStep>(1);
  protected readonly selectedService = signal<BarberService | null>(null);
  protected readonly selectedBarber = signal<Barber | null>(null);
  protected readonly selectedDate = signal<Date | null>(null);
  protected readonly selectedTime = signal<string | null>(null);
  protected readonly calendarMonth = signal(this.startOfMonth(new Date()));
  protected readonly confirmed = signal(false);
  protected readonly successVisible = signal(false);

  protected readonly monthLabel = computed(() =>
    new Intl.DateTimeFormat('pt-BR', { month: 'long', year: 'numeric' }).format(this.calendarMonth()),
  );

  protected readonly calendarDays = computed<CalendarDay[]>(() => {
    const month = this.calendarMonth();
    const first = this.startOfMonth(month);
    const start = new Date(first);
    start.setDate(first.getDate() - first.getDay());

    return Array.from({ length: 42 }, (_, index) => {
      const date = new Date(start);
      date.setDate(start.getDate() + index);

      return {
        date,
        label: date.getDate(),
        inMonth: date.getMonth() === month.getMonth(),
        disabled: this.isPast(date) || date.getDay() === 0,
      };
    });
  });

  protected readonly timeSlots = computed<TimeSlot[]>(() => {
    const date = this.selectedDate();
    if (!date) {
      return [];
    }

    const slots: TimeSlot[] = [];
    for (let hour = 9; hour <= 18; hour++) {
      for (const minute of [0, 30]) {
        if (hour === 18 && minute === 30) {
          continue;
        }

        const time = `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
        const seed = date.getDate() + date.getMonth() + hour + minute;
        slots.push({ time, available: seed % 4 !== 0 });
      }
    }

    return slots;
  });

  protected readonly canProceed = computed(() => {
    switch (this.currentStep()) {
      case 1:
        return !!this.selectedService();
      case 2:
        return !!this.selectedBarber();
      case 3:
        return !!this.selectedDate();
      case 4:
        return !!this.selectedTime();
    }
  });

  protected readonly isComplete = computed(
    () => !!this.selectedService() && !!this.selectedBarber() && !!this.selectedDate() && !!this.selectedTime(),
  );

  protected selectService(service: BarberService): void {
    this.selectedService.set(service);
    this.resetConfirmation();
  }

  protected selectBarber(barber: Barber): void {
    this.selectedBarber.set(barber);
    this.resetConfirmation();
  }

  protected selectDate(day: CalendarDay): void {
    if (day.disabled || !day.inMonth) {
      return;
    }

    this.selectedDate.set(day.date);
    this.selectedTime.set(null);
    this.resetConfirmation();
  }

  protected selectTime(slot: TimeSlot): void {
    if (!slot.available) {
      return;
    }

    this.selectedTime.set(slot.time);
    this.resetConfirmation();
  }

  protected goToStep(step: BookingStep): void {
    if (step < this.currentStep()) {
      this.currentStep.set(step);
    }
  }

  protected nextStep(): void {
    if (!this.canProceed() || this.currentStep() === 4) {
      return;
    }

    this.currentStep.update((step) => (step + 1) as BookingStep);
  }

  protected previousStep(): void {
    if (this.currentStep() > 1) {
      this.currentStep.update((step) => (step - 1) as BookingStep);
    }
  }

  protected changeMonth(offset: number): void {
    const next = new Date(this.calendarMonth());
    next.setMonth(next.getMonth() + offset);
    this.calendarMonth.set(this.startOfMonth(next));
  }

  protected confirmBooking(): void {
    if (!this.isComplete()) {
      return;
    }

    this.confirmed.set(true);
    this.successVisible.set(true);
    window.setTimeout(() => this.successVisible.set(false), 5000);
  }

  protected isSelectedDate(date: Date): boolean {
    const selected = this.selectedDate();
    return !!selected && this.sameDay(selected, date);
  }

  protected iconPath(service: BarberService): string {
    const paths = {
      scissors: 'M14 7 4 17m0-10 10 10M5 5h.01M5 19h.01M19 5l-5 5m5 9-5-5',
      beard: 'M6 8c0 6 3 10 6 10s6-4 6-10M8 9c1 2 7 2 8 0M9 5h6',
      combo: 'M4 17 14 7m-10 0 10 10M17 6h3M18.5 4.5v3M17 17h3M18.5 15.5v3',
      color: 'M12 3v6m-4 8h8l-1-8H9l-1 8Zm2 0v4h4v-4',
      hydration: 'M12 3s5 5 5 9a5 5 0 0 1-10 0c0-4 5-9 5-9Z',
    };

    return paths[service.icon];
  }

  private resetConfirmation(): void {
    this.confirmed.set(false);
    this.successVisible.set(false);
  }

  private startOfMonth(date: Date): Date {
    return new Date(date.getFullYear(), date.getMonth(), 1);
  }

  private isPast(date: Date): boolean {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const target = new Date(date);
    target.setHours(0, 0, 0, 0);
    return target < today;
  }

  private sameDay(first: Date, second: Date): boolean {
    return (
      first.getFullYear() === second.getFullYear() &&
      first.getMonth() === second.getMonth() &&
      first.getDate() === second.getDate()
    );
  }
}
