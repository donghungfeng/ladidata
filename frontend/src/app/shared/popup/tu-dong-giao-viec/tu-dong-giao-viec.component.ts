import { HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DanhMucService } from 'app/danhmuc.service';
import { NotificationService } from 'app/notification.service';
import DateUtil from 'app/shared/util/date.util';
import { jqxGridComponent } from 'jqwidgets-ng/jqxgrid';
import moment from 'moment';
import { userInfo } from 'os';
@Component({
  selector: 'app-tu-dong-giao-viec',
  templateUrl: './tu-dong-giao-viec.component.html',
  styleUrls: ['./tu-dong-giao-viec.component.scss']
})

export class TuDongGiaoViecComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('gridNhanVien') myGrid: jqxGridComponent;
  @ViewChild('gridCongViec') myGridCV: jqxGridComponent;
  REQUEST_WORK_URL ="/api/v1/work";
  listUser = [];
  listWork = [];
  selectId = null;
  REQUEST_DATA_URL ="/api/v1/data";

  // grid nhân viên
  dataAdapter!: any;
  source =
        {
            localdata: [],
            datafields:
            [
                { name: 'id', type: 'number' },
                { name: 'ten', type: 'string' },
                { name: 'tenDayDu', type: 'string' },
                { name: 'thoiGianVao', type: 'string' },
                { name: 'donGiao', type: 'number' },
                { name: 'donHoanThanh', type: 'number' },
                { name: 'soLuongCV', type: 'number' }
            ],
            id:'id',
            datatype: 'array'
        }
    columns: any[] =
    [
        { text: 'Tên đăng nhập', editable: false, datafield: 'ten'},
        { text: 'Họ tên', editable: false, datafield: 'tenDayDu', width:'200'},
        { text: 'Thời gian checkin',editable:false ,datafield: 'thoiGianVao'},
        { text: 'Đơn hoàn thành',editable:false ,datafield: 'donHoanThanh'},
        { text: 'Số lượng đơn giao',editable:false ,datafield: 'soLuongCV'}
    ];
    // grid dùng chung
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
    this.getWorks();
  }

  ngOnDestroy(): void {}

  ngAfterViewInit(): void {
    this.myGrid.pagesizeoptions(this.pageSizeOptions);
  }

  getUserActive() {
    this.service.getOption(null, this.REQUEST_WORK_URL,"/getAllActive").subscribe(
        (res: HttpResponse<any>) => {
          this.listUser = res.body.RESULT;
          this.source.localdata = this.listUser;
          this.dataAdapter = new jqx.dataAdapter(this.source);
          setTimeout(() => {
            this.myGrid.selectallrows();
          }, 500);
        },
        (error: any) => {
          this.notificationService.showError(`${error.body.RESULT.message}`,"Thông báo lỗi!");
        }
      );
  }

  customDataSelect(list: any[]): any[] {
     const count = this.myGrid.getselectedrowindexes().length;
     const listcheck = this.myGrid.getselectedrowindexes();
      const phanDu = this.listWork.length % count;
      const phanNguyen = Math.floor(this.listWork.length / count);
      if(this.listWork.length >= count){
        if(phanDu === 0){
          list.forEach((unitItem,index) => {
            unitItem.ten = unitItem.acount? unitItem.acount.userName:null;
            unitItem.tenDayDu = unitItem.acount? unitItem.acount.fullName:null;
            unitItem.thoiGianVao = unitItem.timeIn? DateUtil.formatDate(unitItem.timeIn):null;
            unitItem.soLuongCV = listcheck.includes(index)?phanNguyen:null;
          });
          return list;
        }else{
          let countListCheck = count;
          list.forEach((unitItem,index) => {
            unitItem.ten = unitItem.acount? unitItem.acount.userName:null;
            unitItem.tenDayDu = unitItem.acount? unitItem.acount.fullName:null;
            unitItem.thoiGianVao = unitItem.timeIn? DateUtil.formatDate(unitItem.timeIn):null;
            if(listcheck.includes(index)){
              countListCheck--;
              unitItem.soLuongCV = (countListCheck === 0)?(phanNguyen + phanDu):phanNguyen;
            }else{
              unitItem.soLuongCV = null;
            }
          });
          return list;
        }
        
      }else{
        let countListCheck = count
        list.forEach((unitItem,index) => {
          unitItem.ten = unitItem.acount? unitItem.acount.userName:null;
          unitItem.tenDayDu = unitItem.acount? unitItem.acount.fullName:null;
          unitItem.thoiGianVao = unitItem.timeIn? DateUtil.formatDate(unitItem.timeIn):null;
          if(listcheck.includes(index)){
            countListCheck --;
            if(countListCheck === 0){
              unitItem.soLuongCV = phanDu;
            }else{
              unitItem.soLuongCV = 0;
            }
          }else{
            unitItem.soLuongCV = null;
          }
        });
        return list;
      }  
  }

  getWorks():void{
    this.service.getOption(null, this.REQUEST_DATA_URL,`?status=0&startDate=0&endDate=99999999999999`).subscribe(
      (res: HttpResponse<any>) => {
          this.listWork = res.body.RESULT;
          if(this.listWork.length === 0){
            this.notificationService.showWarning('Danh sách công việc null','Cảnh báo!');
          }else{
            this.getUserActive();
          }
      },
      (error: HttpResponse<any>) => {
          this.notificationService.showError(`${error.body.RESULT.message}`,"Thông báo lỗi!");
      }
  );
  }

  onRowSelect(e:any):void{
    this.customDataSelect(this.listUser);
    this.source.localdata = this.listUser;
    this.dataAdapter = new jqx.dataAdapter(this.source);
    setTimeout(() => {
      this.myGrid.refreshdata();
    }, 200);
  }

  Rowunselect(e: any): void {
    this.customDataSelect(this.listUser);
    this.source.localdata = this.listUser;
    this.dataAdapter = new jqx.dataAdapter(this.source);
    setTimeout(() => {
      this.myGrid.refreshdata();
    }, 200);
  }

  public dismiss(): void {
    this.activeModal.dismiss();
  }

  public assignWork():void {
    const checkIndex = this.myGrid.getselectedrowindexes();
    if(checkIndex.length === 0){
      this.notificationService.showWarning('Vui lòng chọn ít nhất một tài khoản','Cảnh báo!')
    }
    const listCheck = [];
    for(let i = 0; i< this.listUser.length; i++){
      if(checkIndex.includes(i)){
        listCheck.push(this.listUser[i]);
      }
    }
    const phanNguyen =  Math.floor(this.listWork.length / listCheck.length);
    const length = listCheck.length;
    let countTK = 0;
    let countCV = phanNguyen;
    if(phanNguyen === 0){
      this.listWork.forEach((unitItem) => {
        unitItem.nhanVienId = listCheck[listCheck.length - 1].acount.id;
        unitItem.date = moment(new Date()).format('YYYYMMDDHHmmss')
        unitItem.status = 1;
      });
    }else{
      this.listWork.forEach((unitItem,index) => {
        unitItem.nhanVienId = listCheck[countTK].acount.id;
        unitItem.date = moment(new Date()).format('YYYYMMDDHHmmss')
        unitItem.status = 1;
        if(index === (countCV-1)){
          countCV = countCV + phanNguyen;
          if(countTK !== (length - 1)){
            countTK++;
          }
        }
      });
    }
    this.save(this.listWork);
  }
  save(list:any):void{
    const entity ={
      dataList: list
    }
    this.service.postOption(entity, this.REQUEST_DATA_URL, "/assignWork").subscribe(
      (res: HttpResponse<any>) => {
        this.activeModal.close();
        this.notificationService.showSuccess(`${res.body.MESSAGE}`,"Thông báo!");
      },
      (error: any) => {
        this.notificationService.showError(`${error.body.MESSAGE}`,"Thông báo lỗi!");
      }
    );

  }
}
