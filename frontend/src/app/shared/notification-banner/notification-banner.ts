import { Component } from '@angular/core';

import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-notification-banner',
  standalone: true,
  imports: [],
  templateUrl: './notification-banner.html',
  styleUrl: './notification-banner.scss'
})
export class NotificationBanner {
  constructor(public notificationService: NotificationService) {}
}
