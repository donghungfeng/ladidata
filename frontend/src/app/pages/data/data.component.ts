import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef , AfterViewInit} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DanhMucService } from 'app/danhmuc.service';
import { ConfirmationDialogService } from 'app/layouts/confirm-dialog/confirm-dialog.service';
import { NotificationService } from 'app/notification.service';
import { GiaoViecPopUpComponent } from 'app/shared/popup/giao-viec-pop-up/giao-viec-pop-up.component';
import { TongKetDuLieuPopupComponent } from 'app/shared/popup/tong-ket-du-lieu/TongKetDuLieuPopup.component';
import { TuDongGiaoViecComponent } from 'app/shared/popup/tu-dong-giao-viec/tu-dong-giao-viec.component';
import { XuLyDuLieuPopupComponent } from 'app/shared/popup/xu-ly-du-lieu/XuLyDuLieuPopup.component';
import dayjs, { Dayjs } from 'dayjs/esm';

import { jqxGridComponent } from 'jqwidgets-ng/jqxgrid';
import * as moment from 'moment';
import { DateRanges, TimePeriod } from 'ngx-daterangepicker-material/daterangepicker.component';
import * as XLSX from 'xlsx';  



@Component({
    selector: 'data-cmp',
    templateUrl: 'data.component.html'
})

export class DataComponent implements OnInit, AfterViewInit{
    @ViewChild('gridReference') myGrid: jqxGridComponent;
    @ViewChild('TABLE', { static: false }) TABLE: ElementRef;  

    public searchKey = '';
    source: any
    listStatus = [
        {id: 0,label:"Chờ xử lý"},
        {id: 1,label:"Đang xử lý"},
    ];
	getWidth() : any {
		if (document.body.offsetWidth < 850) {
			return '90%';
		}
		
		return 850;
	}
    dataAdapter: any;
    columns: any[] =
    [
        {
            text: '#', sortable: false, filterable: false, editable: false, width: (window.innerWidth - window.innerWidth * 0.94 - 32),
            groupable: false, draggable: false, resizable: false,
            datafield: '', columntype: 'number', 
            cellsrenderer: (row: number, column: any, value: number): string => {
                return '<div style="position: relative;top: 50%;left: 4px;transform: translateY(-50%);">' + (value + 1) + '</div>';
            }
        },
        { text: 'Ngày', editable: false, datafield: 'ngay', width: '10%'},
        { text: 'Tên KH', editable: false, datafield: 'name', width: '10%'},
        { text: 'Sản phẩm',editable:false ,datafield: 'formcolor' , width: '10%'},
        { text: 'SĐT', editable: false, datafield: 'phone' , width: '10%'},
        { text: 'Địa chỉ', editable: false, datafield: 'street' , width: '10%'},
        { text: 'Xã', editable: false, datafield: 'ward' , width: '8%'},
        { text: 'Huyện', editable: false, datafield: 'district' ,  width: '8%'},
        { text: 'Tỉnh', editable: false, datafield: 'state' ,  width: '8%'},
        { text: 'Trạng thái', editable: false, datafield: 'trangThai' ,  width: '10%'},
        { text: 'Nhân viên', editable: false, datafield: 'nhanvienid' ,  width: '10%'},

    ];
    height: any = $(window).height()! - 270;
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

    REQUEST_URL ="/api/v1/data";

    listEntity = [];

    selectedEntity:any;

    constructor(
        private dmService: DanhMucService,
        private notificationService: NotificationService,
        private confirmDialogService: ConfirmationDialogService,
        private modalService: NgbModal
    ){
        this.source =
        {
            localdata: [],
            datafields:
            [
                { name: 'id', type: 'number' },
                { name: 'name', type: 'string' },
                { name: 'phone', type: 'string' },
                { name: 'street', type: 'string' },
                { name: 'ward', type: 'string' },
                { name: 'state', type: 'string' },
                { name: 'district', type: 'string' },
                { name: 'status', type: 'number' },
                { name: 'nhanvien', type: 'string' },
                { name: 'date', type: 'date',format: "DD/MM/YYYY" },
                { name: 'formcolor', type: 'string' },
                { name: 'nhanvienid', type: 'number' },
                { name: 'ngay', type: 'string' },
                { name: 'trangThai', type: 'string' }
            ],
            id:'id',
            datatype: 'array'
        };
        this.dataAdapter = new jqx.dataAdapter(this.source);

        this.dateRange = {
            startDate: dayjs().subtract(1, 'days').set('hours', 0).set('minutes', 0),
            endDate: dayjs().subtract(1, 'days').set('hours', 23).set('minutes', 59)
          };
    }

    ngOnInit(){ 
        this.loadData();
    }

    ngAfterViewInit(): void {
        this.myGrid.pagesizeoptions(this.pageSizeOptions);
      }

    public loadData(){
        this.dmService.getOption(null, this.REQUEST_URL,"/getAll").subscribe(
            (res: HttpResponse<any>) => {
              setTimeout(() => {
                this.source.localdata = this.customDate(res.body.RESULT);
                this.dataAdapter = new jqx.dataAdapter(this.source);
              }, 100);
            },
            () => {
              console.error();
            }
          );
    }

    customDate(list: any[]): any[] {
        list.forEach(unitItem => {
            unitItem.ngay = unitItem.date? moment(unitItem.date, 'X').format('DD/MM/YYYY HH:mm:ss'):null;
            switch (unitItem.status){
                case 0: 
                {
                    unitItem.trangThai = 'Chờ xử lý';
                    break;
                }
                case 1: 
                {
                    unitItem.trangThai = 'Đang xử lý';
                    break;
                }
                case 2: 
                {
                    unitItem.trangThai = 'Hoàn thành';
                    break;
                }
                case 3: 
                {
                    unitItem.trangThai = 'Delay';
                    break;
                }
                case 4: 
                {
                    unitItem.trangThai = 'Hủy';
                    break;
                }
                case 5: 
                {
                    unitItem.trangThai = 'Gửi giao hàng';
                    break;
                }
                default:
                {
                    unitItem.trangThai = '';
                    break;
                }
            }
        });
        return list;
      }

    listWork = [];

    public showData(){
        let indexs = this.myGrid.getselectedrowindexes();
        this.listWork = [];
        if (indexs.length > 0 && this.listWork.length == 0)
        {
            for(let i = 0; i <indexs.length; i++) {
                this.listWork.push(this.myGrid.getrowdata(indexs[i]));
            }
        }

        if(this.listWork.length > 0 ) {
            const modalRef = this.modalService.open(GiaoViecPopUpComponent, { size: 'xl' });
            modalRef.componentInstance.data = this.listWork;
        }
        else this.notificationService.showError('Vui lòng chọn công việc',"Thông báo lỗi!");
    }

    openAutoAssignWork():void{
        const modalRef = this.modalService.open(TuDongGiaoViecComponent, { windowClass: 'modal-view',keyboard: true });
        modalRef.result.then(
            () => {
              this.loadData();
            },
            () => {}
          );
    }

    public onProcessData(event:any):void{
        if(!this.selectedEntity) {
            this.notificationService.showError('Vui lòng chọn dữ liệu',"Thông báo lỗi!");
            return;
        }
        const modalRef = this.modalService.open(XuLyDuLieuPopupComponent, { size: 'xl' });
        modalRef.componentInstance.data = this.selectedEntity;
        modalRef.result.then(
            () => {
              this.loadData();
             
            },
            () => {}
          );
    }
    public onExportData():void{
        const modalRef = this.modalService.open(TongKetDuLieuPopupComponent, { size: 'xl' });
        modalRef.componentInstance.data = this.listEntity;
        modalRef.result.then(
            () => {
              this.loadData();
            },
            () => {}
          );
    }
    public onRowSelect(event:any):void{
        this.selectedEntity = event.args.row;
    }


    public exportTOExcel() {  
        const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.TABLE.nativeElement);  
        const wb: XLSX.WorkBook = XLSX.utils.book_new();  
        XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');  
        XLSX.writeFile(wb, `data_${this.getCurrentDate()}.xlsx`); 
    } 

    private getCurrentDate() {
        let date = new Date();
        let month = date.getMonth() + 1;

        var m = String(month);
        if (month < 10) {
            m = "0" + month.toString();
        }

        let dateOfMonth = date.getDate();
        var day = String(dateOfMonth);

        if (dateOfMonth < 10) {
            day = "0" + dateOfMonth;
        }

        let year = date.getFullYear();
        
        let formattedDate = year + "/" + m + "/" + day;
        return formattedDate;
    }

    public searchData() {
        if(this.searchKey === '') this.notificationService.showError('Vui lòng nhập từ khóa tìm kiếm',"Thông báo lỗi!");

        this.dmService.getOption(null, this.REQUEST_URL,"/filterData").subscribe(
            (res: HttpResponse<any>) => {
              setTimeout(() => {
                this.source.localdata = res.body.RESULT;
                this.dataAdapter = new jqx.dataAdapter(this.source);
              }, 100);
            },
            (error: HttpResponse<any>) => {
                this.notificationService.showError(`${error.body.RESULT}`,"Thông báo lỗi!");
            }
          );
    }


    statusDto: any = -1;
    public getByStatus(status: any) {
        this.statusDto = status;
        var date = JSON.parse(JSON.stringify(this.dateRange));
        console.log(status);
        let startDate = moment(new Date(date.startDate)).format('DD/MM/YYYY');
        let endDate = moment(new Date(date.endDate)).format('DD/MM/YYYY');
        if(status == -1) {
            this.dmService.getOption(null, this.REQUEST_URL,`/getByDate?startDate=${startDate}&endDate=${endDate}`).subscribe(
                (res: HttpResponse<any>) => {
                //   setTimeout(() => {
                //     // res.body.RESULT.forEach(obj=> {
                //     //     moment.locale("vi"); 
                //     //     obj.date = moment(obj.date).format('MMMM Do YYYY, h:mm:ss a');
                //     // });
                //     this.source.localdata = res.body.RESULT;
                //     this.dataAdapter = new jqx.dataAdapter(this.source);
                //   }, 100);
                },
                (error: HttpResponse<any>) => {
                    this.notificationService.showError(`${error.body.RESULT.message}`,"Thông báo lỗi!");
                }
            );
        }else {
            

            this.dmService.getOption(null, this.REQUEST_URL,`/getByStatus?status=${this.statusDto}&startDate=${startDate}&endDate=${endDate}`).subscribe(
                (res: HttpResponse<any>) => {
                  setTimeout(() => {
                    // res.body.RESULT.forEach(obj=> {
                    //     moment.locale("vi"); 
                    //     obj.date = moment(obj.date).format('MMMM Do YYYY, h:mm:ss a');
                    // });
                    this.source.localdata = res.body.RESULT;
                    this.dataAdapter = new jqx.dataAdapter(this.source);
                  }, 100);
                },
                (error: HttpResponse<any>) => {
                    this.notificationService.showError(`${error.body.RESULT.message}`,"Thông báo lỗi!");
                }
            );
        }
    }



    /* Date Range Picker*/
    dateRange: TimePeriod;
    date: object;
    ranges: DateRanges = {
        ['Hôm nay']: [dayjs(), dayjs()],
        ['Hôm qua']: [dayjs().subtract(1, 'days'), dayjs().subtract(1, 'days')],
        ['7 Ngày qua']: [dayjs().subtract(6, 'days'), dayjs()],
        ['30 Ngày qua']: [dayjs().subtract(29, 'days'), dayjs()],
        ['Tháng này']: [dayjs().startOf('month'), dayjs().endOf('month')],
        ['Tháng trước']: [dayjs().subtract(1, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')],
        ['3 Tháng trước']: [dayjs().subtract(3, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')]
    };

    public convertDateToString(date:any):string {
        return moment(date).format('yyyyMMddHHmmss');
    }
    /* End Date Range Picker */
}
