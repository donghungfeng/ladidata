import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ToastrModule } from "ngx-toastr";

import { SidebarModule } from './sidebar/sidebar.module';
import { FooterModule } from './shared/footer/footer.module';
import { NavbarModule} from './shared/navbar/navbar.module';
import { FixedPluginModule} from './shared/fixedplugin/fixedplugin.module';

import { AppComponent } from './app.component';
import { AppRoutes } from './app.routing';
import { FormsModule } from "@angular/forms";

import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { QRCodeModule } from "angularx-qrcode";
import { XuLyDuLieuPopupComponent } from "./shared/popup/xu-ly-du-lieu/XuLyDuLieuPopup.component";
import { TongKetDuLieuPopupComponent } from "./shared/popup/tong-ket-du-lieu/TongKetDuLieuPopup.component";
import { jqxDateTimeInputModule } from 'jqwidgets-ng/jqxdatetimeinput';
import { LogiinComponent } from './pages/logiin/logiin.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { HeadersInterceptor } from "./headers.interceptor";
import { ThemSuaXoaAccountComponent } from './shared/popup/them-sua-xoa-account/them-sua-xoa-account.component';
import { GiaoViecPopUpComponent } from './shared/popup/giao-viec-pop-up/giao-viec-pop-up.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { TuDongGiaoViecComponent } from "./shared/popup/tu-dong-giao-viec/tu-dong-giao-viec.component";
import { jqxGridModule } from "jqwidgets-ng/jqxgrid";


@NgModule({
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    XuLyDuLieuPopupComponent,
    TongKetDuLieuPopupComponent,
    LogiinComponent,
    ThemSuaXoaAccountComponent,
    GiaoViecPopUpComponent,
    TuDongGiaoViecComponent
  ],
  imports: [
    BrowserAnimationsModule,
    RouterModule.forRoot(AppRoutes,{
      useHash: true
    }),
    SidebarModule,
    NavbarModule,
    ToastrModule.forRoot(),
    FooterModule,
    FixedPluginModule,
    NgbModule,
    HttpClientModule,
    QRCodeModule,
    jqxDateTimeInputModule,
    FormsModule,
    NgxWebstorageModule.forRoot({ prefix: '', separator: '' }),
    NgSelectModule,
    jqxGridModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HeadersInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
  exports: [XuLyDuLieuPopupComponent,TongKetDuLieuPopupComponent]
})
export class AppModule { }
