import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ServicosService } from '../../services/servicos.service';
import { DISPLAY_SERVICES } from '../../data/mock-booking.data';
import { BarberService } from '../../models/booking.models';

@Component({
  selector: 'app-services-section',
  imports: [CurrencyPipe],
  templateUrl: './services-section.component.html',
  styleUrl: './services-section.component.css',
})
export class ServicesSectionComponent implements OnInit {
  private readonly servicosService = inject(ServicosService);

  protected services = DISPLAY_SERVICES;

  ngOnInit(): void {
    this.servicosService.listar().subscribe((lista) => {
      if (lista.length > 0) {
        this.services = lista.slice(0, 4);
      }
    });
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
}
