import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

import { MenuModule } from 'primeng/menu';
import { TableModule } from 'primeng/table';
import { DataTableModule } from 'primeng/datatable';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import {
  ConfirmationService,
  ConfirmDialogModule,
  GrowlModule, InputTextareaModule,
  InputTextModule, ProgressBarModule, SelectButtonModule, TabViewModule
} from 'primeng/primeng';
import { NewsTableComponent } from './news-table/news-table.component';

@NgModule({
  declarations: [
    AppComponent,
    NewsTableComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MenuModule,
    TableModule,
    DataTableModule,
    ButtonModule,
    DialogModule,
    FormsModule,
    InputTextModule,
    InputTextareaModule,
    ConfirmDialogModule,
    GrowlModule,
    ProgressBarModule,
    SelectButtonModule,
    TabViewModule
  ],
  providers: [ConfirmationService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
