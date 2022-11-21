import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { ROUTES } from '../../sidebar/sidebar.component';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { LogiinComponent } from 'app/pages/logiin/logiin.component';
import { left } from '@popperjs/core';
import { LocalStorageService } from 'ngx-webstorage';
import { DanhMucService } from 'app/danhmuc.service';
import { HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
@Component({
  moduleId: module.id,
  selector: 'navbar-cmp',
  templateUrl: 'navbar.component.html'
})


export class NavbarComponent implements OnInit {
  private listTitles: any[];
  location: Location;
  private nativeElement: Node;
  private toggleButton;
  private sidebarVisible: boolean;
  public checkWorkActive = false;

  public isCollapsed = true;
  @ViewChild("navbar-cmp", { static: false }) button;

  constructor(location: Location, private renderer: Renderer2, private element: ElementRef, private router: Router,
    private local: LocalStorageService,
    private dmService: DanhMucService) {
    this.location = location;
    this.nativeElement = element.nativeElement;
    this.sidebarVisible = false;
    let entity = {
      nhanVienId: this.local.retrieve("authenticationToken").id
    };
    this.dmService.postOption(entity, "/api/v1/work/checkWorkActive", '').subscribe(
      (res: HttpResponse<any>) => {
        if (res.body.CODE === 200) {
          this.checkWorkActive = true;
        }
      },
      () => {
        console.error();
      }
    );
  }

  ngOnInit() {
    this.listTitles = ROUTES.filter(listTitle => listTitle);
    var navbar: HTMLElement = this.element.nativeElement;
    this.toggleButton = navbar.getElementsByClassName('navbar-toggle')[0];
    this.router.events.subscribe((event) => {
      this.sidebarClose();
    });
  }
  async triggerWorkActive() {
    moment.locale("vi");
    let time = moment(new Date).format('YYYYMMhms');

    if (this.checkWorkActive == false) {
      let checkInEntity = {
        timeIn: time,
        nhanVienId: this.local.retrieve("authenticationToken").id
      };
      this.dmService.postOption(checkInEntity, "/api/v1/work/", '').subscribe(
        (res: HttpResponse<any>) => {
          if (res.body.CODE === 200) {
            this.checkWorkActive = true;
          }
        },
        () => {
          console.error();
        }
      );
    } else {
      let id;
      this.dmService.postOption({ nhanVienId: this.local.retrieve("authenticationToken").id }, "/api/v1/work/checkWorkActive", '').subscribe(
        (res: HttpResponse<any>) => {
          if (res.body.CODE === 200) {
            id = res.body.RESULT.acount.id;
          }
          console.log(id)
        },
        () => {
          console.error();
        }
      );
      setTimeout(() => {
        console.log(id);
        this.checkOut(id);
      }, 500);
    }
  }

  checkOut(id: any) {
    let time = moment(new Date).format('YYYYMMhms');
    let checkOutEntity = {
      id: id,
      timeOut: time,
    };

    this.dmService.postOption(checkOutEntity, "/api/v1/work/checkOut/", '').subscribe(
      (res: HttpResponse<any>) => {
        if (res.body.CODE === 200) {
          this.checkWorkActive = false;
        }
      },
      () => {
        console.error();
      }
    );

  }
  getTitle() {
    var titlee = this.location.prepareExternalUrl(this.location.path());
    if (titlee.charAt(0) === '#') {
      titlee = titlee.slice(1);
    }
    for (var item = 0; item < this.listTitles.length; item++) {
      if (this.listTitles[item].path === titlee) {
        return this.listTitles[item].title;
      }
    }
    return 'Dashboard';
  }
  sidebarToggle() {
    if (this.sidebarVisible === false) {
      this.sidebarOpen();
    } else {
      this.sidebarClose();
    }
  }
  sidebarOpen() {
    const toggleButton = this.toggleButton;
    const html = document.getElementsByTagName('html')[0];
    const mainPanel = <HTMLElement>document.getElementsByClassName('main-panel')[0];
    setTimeout(function () {
      toggleButton.classList.add('toggled');
    }, 500);

    html.classList.add('nav-open');
    // if(window.innerWidth < 1920){
    //   mainPanel.style.width = '83%';
    // }
    if (window.innerWidth < 991) {
      mainPanel.style.position = 'fixed';
    }
    this.sidebarVisible = true;
  };
  sidebarClose() {
    const html = document.getElementsByTagName('html')[0];
    const mainPanel = <HTMLElement>document.getElementsByClassName('main-panel')[0];
    // if(window.innerWidth < 1920){
    //   mainPanel.style.width = '100%';
    // }
    if (window.innerWidth < 991) {
      setTimeout(function () {
        mainPanel.style.position = '';
      }, 500);
    }
    this.toggleButton.classList.remove('toggled');
    this.sidebarVisible = false;
    html.classList.remove('nav-open');
  };
  collapse() {
    this.isCollapsed = !this.isCollapsed;
    const navbar = document.getElementsByTagName('nav')[0];
    console.log(navbar);
    if (!this.isCollapsed) {
      navbar.classList.remove('navbar-transparent');
      navbar.classList.add('bg-white');
    } else {
      navbar.classList.add('navbar-transparent');
      navbar.classList.remove('bg-white');
    }

  }
  logout() {
    this.local.clear();
    setTimeout(() => {
      this.router.navigate(['/logiin']);
    }, 200);
  }
}
