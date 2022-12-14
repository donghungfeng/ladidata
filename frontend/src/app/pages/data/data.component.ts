import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef , AfterViewInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DanhMucService } from 'app/danhmuc.service';
import { ConfirmationDialogService } from 'app/layouts/confirm-dialog/confirm-dialog.service';
import { NotificationService } from 'app/notification.service';
import { ChuyenTrangThaiPopUpComponent } from 'app/shared/popup/chuyen-trang-thai-pop-up/chuyen-trang-thai-pop-up.component';
import { GiaoViecPopUpComponent } from 'app/shared/popup/giao-viec-pop-up/giao-viec-pop-up.component';
import { TongKetDuLieuPopupComponent } from 'app/shared/popup/tong-ket-du-lieu/TongKetDuLieuPopup.component';
import { TuDongGiaoViecComponent } from 'app/shared/popup/tu-dong-giao-viec/tu-dong-giao-viec.component';
import { XuLyDuLieuPopupComponent } from 'app/shared/popup/xu-ly-du-lieu/XuLyDuLieuPopup.component';
import DateUtil from 'app/shared/util/date.util';
import dayjs from 'dayjs/esm';

import { jqxGridComponent } from 'jqwidgets-ng/jqxgrid';
import * as moment from 'moment';
import { DateRanges, TimePeriod } from 'ngx-daterangepicker-material/daterangepicker.component';
import { LocalStorageService } from 'ngx-webstorage';
import * as XLSX from 'xlsx';  

@Component({
    selector: 'data-cmp',
    templateUrl: 'data.component.html'
})

export class DataComponent implements OnInit, AfterViewInit{
    @ViewChild('gridReference') myGrid: jqxGridComponent;
    @ViewChild('TABLE', { static: false }) TABLE: ElementRef;  
    // grid
    source: any
    dataAdapter: any;
    columns: any[] =
    [
        {
            text: '#', sortable: false, filterable: false, editable: false, width: '5%',
            groupable: false, draggable: false, resizable: false,
            datafield: '', columntype: 'number', 
            cellsrenderer: (row: number, column: any, value: number): string => {
                return '<div style="position: relative;top: 50%;left: 4px;transform: translateY(-50%);">' + (value + 1) + '</div>';
            }
        },
        { text: 'Ng??y', editable: false, datafield: 'ngay', width: '10%'},
        { text: 'T??n KH', editable: false, datafield: 'name', width: '15%'},
        { text: 'S??T', editable: false, datafield: 'phone' , width: '10%'},
        { text: 'S???n ph???m',editable:false ,datafield: 'product' , width: '25%'},
        { text: 'Tr???ng th??i', editable: false, datafield: 'status' ,  width: '10%',cellsrenderer: (row: number, column: any, value: number): string => {
            switch (value){
                case 0: 
                {
                    return '<div class="div-center bg-light">' + 'M???i' + '</div>';
                }
                case 1: 
                {
                    return '<div class = "bg-info div-center text-white">' + '???? ti???p nh???n' + '</div>';
                }
                case 2: 
                {
                    return '<div class = "bg-primary div-center text-white">' + '??ang x??? l??' + '</div>';
                }
                case 3: 
                {
                    return '<div class = "bg-warning div-center">' + 'KNM L1' + '</div>';
                }
                case 4: 
                {
                    return '<div class = "bg-warning div-center">' + 'KNM L2' + '</div>';
                }
                case 5: 
                {
                    return '<div class = "bg-warning div-center">' + 'KNM L3' + '</div>';
                }
                case 6: 
                {
                    return '<div class = "bg-danger div-center text-white">' + 'Th???t b???i' + '</div>';
                }
                case 7: 
                {
                    return '<div class = "bg-success div-center text-white">' + 'Th??nh c??ng' + '</div>';
                }
                case 8: 
                {
                    return '<div class = "bg-success div-center text-white">' + '???? in ????n' + '</div>';
                }
                default:
                {
                    return '<div></div>';
                }
            }
        }},
        { text: 'Nh??n vi??n', editable: false, datafield: 'nhanvien' ,  width: '12%'},
        { text: 'Doanh s???',editable:false ,datafield: 'price' , width: '10%', cellsrenderer: (row: number, column: any, value: number): string => 
        {
            return '<div>' + value.toLocaleString('vi', {style : 'currency', currency : 'VND'}) + '</div>'
        }
        },
        // { text: 'Ngu???n', editable: false, datafield: 'link' , width: '30%'},
        // { text: '?????a ch???', editable: false, datafield: 'street' , width: '10%'},
        // { text: 'X??', editable: false, datafield: 'ward' , width: '8%'},
        // { text: 'Huy???n', editable: false, datafield: 'district' ,  width: '8%'},
        // { text: 'T???nh', editable: false, datafield: 'state' ,  width: '8%'},
        // { text: 'Ghi ch??', editable: false, datafield: 'message' , width: '12%'},

    ];
    height: any = $(window).height()! - 240;
    localization: any = {
      pagergotopagestring: 'Trang',
      pagershowrowsstring: 'Hi???n th???',
      pagerrangestring: ' c???a ',
      emptydatastring: 'Kh??ng c?? d??? li???u hi???n th???',
      filterstring: 'N??ng cao',
      filterapplystring: '??p d???ng',
      filtercancelstring: 'Hu??? b???'
    };
    pageSizeOptions = ['10', '25', '50', '100'];
    // chung
    REQUEST_URL ="/api/v1/data";
    listEntity = [];
    selectedEntity:any;
    public searchKey = '';
    statusDto: any = '';
    data:any = [];
    // date
    dateRange: TimePeriod = {
        startDate: dayjs().startOf('month'),
        endDate: dayjs().endOf('month')
      };;
    date: object;
    ranges: DateRanges = {
        ['H??m nay']: [dayjs(), dayjs()],
        ['H??m qua']: [dayjs().subtract(1, 'days'), dayjs().subtract(1, 'days')],
        ['7 Ng??y qua']: [dayjs().subtract(6, 'days'), dayjs()],
        ['30 Ng??y qua']: [dayjs().subtract(29, 'days'), dayjs()],
        ['Th??ng n??y']: [dayjs().startOf('month'), dayjs().endOf('month')],
        ['Th??ng tr?????c']: [dayjs().subtract(1, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')],
        ['3 Th??ng tr?????c']: [dayjs().subtract(3, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')]
    };

    info:any;
    countList = [0,0,0,0,0,0,0,0];
    shopCode = '';
    tongDoanhSo = 0;

    constructor(
        private dmService: DanhMucService,
        private notificationService: NotificationService,
        private confirmDialogService: ConfirmationDialogService,
        private modalService: NgbModal,
        private localStorage: LocalStorageService,
        private route: ActivatedRoute
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
                { name: 'country', type: 'string' },
                { name: 'ward', type: 'string' },
                { name: 'state', type: 'string' },
                { name: 'district', type: 'string' },
                { name: 'status', type: 'number' },
                { name: 'date', type: 'string'},
                { name: 'product', type: 'string' },
                { name: 'link', type: 'string' },
                { name: 'nhanvien', type: 'string' },
                { name: 'ngay', type: 'string' },
                { name: 'message', type: 'string' },
                { name: 'ipAddress', type: 'string' },
                { name: 'dateChanged', type: 'string' },
                { name: 'staffName', type: 'string' },
                { name: 'price', type: 'number' },
                { name: 'nhanVienId', type: 'number' },
                { name: 'shopCode', type:'string'}
            ],
            id:'id',
            datatype: 'array'
        };
        this.dataAdapter = new jqx.dataAdapter(this.source);

        this.info = this.localStorage.retrieve("authenticationToken");
    }

    ngOnInit(){
        const interval = setInterval(() => {
            this.loadData()
        }, 60000);

        this.route.queryParams
        .subscribe(params => {
            console.log(params); // { orderby: "price" }
            this.shopCode = params.shopCode;
            this.loadData();
        }
        );
    }

    ngAfterViewInit(): void {
        this.myGrid.pagesizeoptions(this.pageSizeOptions);
      }

      getByStatus(e):void{
        this.statusDto = e;
        this.loadData();
      }

    public loadData(){
        if(!this.shopCode)
        return;
        var date = JSON.parse(JSON.stringify(this.dateRange));
        let startDate = moment(date.startDate).format('YYYYMMDD') + '000000';
        let endDate = moment(date.endDate).format('YYYYMMDD') + '000000';
        const status = (this.statusDto !== '') ? this.statusDto : '0,1,2,3,4,5,6,7,8';
        this.dmService.getOption(null, this.REQUEST_URL,"?status=" + status + '&startDate=' + startDate + '&endDate=' + endDate +'&shopCode='+this.shopCode ).subscribe(
            (res: HttpResponse<any>) => {
              setTimeout(() => {
                this.listEntity = res.body.RESULT;
                this.data = this.customDate(res.body.RESULT,this.statusDto)
                this.source.localdata = this.data;
                this.dataAdapter = new jqx.dataAdapter(this.source);
              }, 100);
            },
            () => {
              console.error();
            }
          );
    }

    customDate(list: any[], status:any): any[] {
        this.tongDoanhSo = 0;
        if(status!=='') this.countList[Number(status)] = 0
        else this.countList = [0,0,0,0,0,0,0,0,0];
        list.forEach(unitItem => {
            unitItem.ngay = unitItem.date? DateUtil.formatDate(unitItem.date):null;
            unitItem.nhanvien = unitItem.account? unitItem.account.userName:'';
            unitItem.nhanVienId = unitItem.account? unitItem.account.id:'';
            this.countList[unitItem.status]++;
            this.tongDoanhSo += Number(unitItem.price);
        });
        return list;
      }

    public assignWork(){
        let indexs = this.myGrid.getselectedrowindexes();
        if(indexs.length === 0){
            this.notificationService.showWarning('Vui l??ng ch???n c??ng vi???c',"C???nh b??o!");
            return;
        }
        const listWork = [];
            for(let i = 0; i <this.listEntity.length; i++) {
                if(indexs.includes(i)){
                    listWork.push(this.listEntity[i]);
                }
            }

        const modalRef = this.modalService.open(GiaoViecPopUpComponent, { windowClass: 'modal-view',keyboard: true });
        modalRef.componentInstance.data = listWork;
        modalRef.componentInstance.shopCode = this.shopCode;
        modalRef.result.then(
            () => {
              this.loadData();
             
            },
            () => {}
          );
    }

    public openChangeStatus(){
        let indexs = this.myGrid.getselectedrowindexes();
        if(indexs.length === 0){
            this.notificationService.showWarning('Vui l??ng ch???n c??ng vi???c',"C???nh b??o!");
            return;
        }
        const listWork = [];
            for(let i = 0; i <this.listEntity.length; i++) {
                if(indexs.includes(i)){
                    listWork.push(this.listEntity[i]);
                }
            }

        const modalRef = this.modalService.open(ChuyenTrangThaiPopUpComponent, { windowClass: 'modal-view',keyboard: true });
        modalRef.componentInstance.data = listWork;
        modalRef.result.then(
            () => {
              this.loadData();
             
            },
            () => {}
          );
    }

    openAutoAssignWork():void{
        const modalRef = this.modalService.open(TuDongGiaoViecComponent, { windowClass: 'modal-view',keyboard: true });
        modalRef.componentInstance.shopCode = this.shopCode;
        modalRef.result.then(
            () => {
              this.loadData();
            },
            () => {}
          );
    }

    

    public onProcessData():void{
        if(!this.selectedEntity) {
            this.notificationService.showWarning('Vui l??ng ch???n d??? li???u',"C???nh b??o!");
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
        this.selectedEntity = this.listEntity[Number(event.args.rowindex)];
    }


    public exportTOExcel() {  
        const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.TABLE.nativeElement);  
        const wb: XLSX.WorkBook = XLSX.utils.book_new();  
        XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');  
        XLSX.writeFile(wb, `data_${this.getCurrentDate()}.xlsx`); 
    } 

    private getCurrentDate() {
        let date = new Date();
        return moment(date).format('DD/MM/YYYY');
    }

    public onRowdblclick(event:any){
        console.log(event);
        const modalRef = this.modalService.open(XuLyDuLieuPopupComponent, { size: 'xl' });
        modalRef.componentInstance.data = this.listEntity[Number(event.args.rowindex)];
        modalRef.result.then(
            () => {
              this.loadData();
             
            },
            () => {}
        );
    }

    refresh():void{
        this.statusDto = '';
        this.loadData();
    }

    formatDate(date:any){
        return DateUtil.formatDate(date);
    }
}
