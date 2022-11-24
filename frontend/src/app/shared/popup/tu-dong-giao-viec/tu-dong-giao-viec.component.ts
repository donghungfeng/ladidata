import { HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DanhMucService } from 'app/danhmuc.service';
import { NotificationService } from 'app/notification.service';
import DateUtil from 'app/shared/util/date.util';
import { jqxGridComponent } from 'jqwidgets-ng/jqxgrid';
@Component({
  selector: 'app-tu-dong-giao-viec',
  templateUrl: './tu-dong-giao-viec.component.html',
  styleUrls: ['./tu-dong-giao-viec.component.scss']
})

export class TuDongGiaoViecComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('gridNhanVien') myGrid: jqxGridComponent;
  REQUEST_WORK_URL ="/api/v1/work";
  listUser = [];
  listWork = [];
  REQUEST_DATA_URL ="/api/v1/data";

  // grid
  dataAdapter!: any;
  source =
        {
            localdata: [],
            datafields:
            [
                { name: 'id', type: 'number' },
                { name: 'ten', type: 'string' },
                { name: 'tenDayDu', type: 'string' },
                { name: 'thoiGianVao', type: 'string' }
            ],
            id:'id',
            datatype: 'array'
        }
    columns: any[] =
    [
        { text: 'Tên đăng nhập', editable: false, datafield: 'ten'},
        { text: 'Họ tên', editable: false, datafield: 'tenDayDu', width:'200'},
        { text: 'thời gian checkin',editable:false ,datafield: 'thoiGianVao'}
    ];
    height: any = 500;
    localization: any = {
      pagergotopagestring: 'Trang',
      pagershowrowsstring: 'Hiển thị',
      pagerrangestring: ' của ',
      emptydatastring: 'Không có dữ liệu hiển thị',
      filterstring: 'Nâng cao',
      filterapplystring: 'Áp dụng',
      filtercancelstring: 'Huỷ bỏ'
    };
    pageSizeOptions = ['50', '100', '200'];

  constructor(private activeModal: NgbActiveModal,
    private service: DanhMucService,
    private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.getUserActive();
    this.getWorks();
  }

  ngOnDestroy(): void {}

  ngAfterViewInit(): void {
    this.myGrid.pagesizeoptions(this.pageSizeOptions);
  }

  getUserActive() {
    this.service.getOption(null, this.REQUEST_WORK_URL,"/getAllActive").subscribe(
        (res: HttpResponse<any>) => {
          this.listUser = this.customDate(res.body.RESULT);
          this.source.localdata = this.listUser;
          this.dataAdapter = new jqx.dataAdapter(this.source);
          setTimeout(() => {
            this.myGrid.selectallrows();
          }, 200);
        },
        (error: any) => {
          this.notificationService.showError(`${error.body.RESULT.message}`,"Thông báo lỗi!");
        }
      );
  }
  customDate(list: any[]): any[] {
    list.forEach(unitItem => {
        unitItem.ten = unitItem.acount? unitItem.acount.userName:null;
        unitItem.tenDayDu = unitItem.acount? unitItem.acount.fullName:null;
        unitItem.thoiGianVao = unitItem.timeIn? DateUtil.formatDate(unitItem.timeIn):null;
    });
    return list;
  }

  getWorks():void{
    this.service.getOption(null, this.REQUEST_DATA_URL,`?status=0&startDate=0&endDate=0`).subscribe(
      (res: HttpResponse<any>) => {
          this.listWork = res.body.RESULT;
      },
      (error: HttpResponse<any>) => {
          this.notificationService.showError(`${error.body.RESULT.message}`,"Thông báo lỗi!");
      }
  );
  }

  onRowSelect(e:any):void{
    console.log(e);
  }

  public dismiss(): void {
    this.activeModal.dismiss();
  }

  public assignWork():void {
    // if(this.staffId <=0 || this.staffId == undefined) {
    //   this.notificationService.showError('Vui lòng chọn nhân sự',"Thông báo lỗi!");
    // }

    // let obj  = {
    //   staffId: this.staffId,
    //   data: this.data
    // }

    // this.service.postOption(obj, this.REQUEST_DATA_URL, "/assignWork").subscribe(
    //   (res: HttpResponse<any>) => {
    //     this.activeModal.dismiss();
    //     this.notificationService.showSuccess(`${res.body.MESSAGE}`,"Thông báo!");
    //   },
    //   (error: any) => {
    //     this.notificationService.showError(`${error.body.MESSAGE}`,"Thông báo lỗi!");
    //   }
    // );
  }
}
