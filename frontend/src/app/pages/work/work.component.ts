import { HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DanhMucService } from 'app/danhmuc.service';
import { ConfirmationDialogService } from 'app/layouts/confirm-dialog/confirm-dialog.service';
import { NotificationService } from 'app/notification.service';
import DateUtil from 'app/shared/util/date.util';
import dayjs from 'dayjs';
import { jqxGridComponent } from 'jqwidgets-ng/jqxgrid';
import moment from 'moment';
import { DateRanges, TimePeriod } from 'ngx-daterangepicker-material/daterangepicker.component';
@Component({
    moduleId: module.id,
    selector: 'work-cmp',
    templateUrl: 'work.component.html'
})

export class WorkComponent implements OnInit, AfterViewInit {
    @ViewChild('gridReference') myGrid: jqxGridComponent;
    source: any
    listStatus = [
        {id: 0,label:"Chờ xử lý"},
        {id: 1,label:"Đang xử lý"},
    ];
    dataAdapter: any;
    columns: any[] =
    [
        {
            text: '#', sortable: false, filterable: false, editable: false,
            groupable: false, draggable: false, resizable: false,
            datafield: '', columntype: 'number', width: 50,
            cellsrenderer: (row: number, column: any, value: number): string => {
                return '<div style="margin: 4px;">' + (value + 1) + '</div>';
            }
        },
        { text: 'timeIn', editable: false, datafield: 'ngayVao'},
        { text: 'timeOut', editable: false, datafield: 'ngayRa'},
        { text: 'Đơn Giao',editable:false ,datafield: 'donGiao' },
        { text: 'Đơn Hoàn Thành', editable: false, datafield: 'donHoanThanh'},
        { text: 'Tài Khoản', editable: false, datafield: 'userName'}

    ];
    height: any = $(window).height()! - 240;
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
    
    // date
    dateRange: TimePeriod = {
        startDate: dayjs().startOf('day'),
        endDate: dayjs().endOf('day')
      };;
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
    REQUEST_URL ="/api/v1/work";

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
                { name: 'ngayVao', type: 'string' },
                { name: 'ngayRa', type: 'string' },
                { name: 'donGiao', type: 'string' },
                { name: 'donHoanThanh', type: 'string' },
                { name: 'ghiChu', type: 'string' },
                { name: 'userName', type: 'any' },
            ],
            id:'id',
            datatype: 'array'
        };
        this.dataAdapter = new jqx.dataAdapter(this.source);
    }

    ngOnInit(){
        this.loadData();
    }
    ngAfterViewInit(): void {
        this.myGrid.pagesizeoptions(this.pageSizeOptions);
      }
    public loadData(){
        var date = JSON.parse(JSON.stringify(this.dateRange));
        let startDate = moment(date.startDate).format('YYYYMMDD') + '000000';
        let endDate = moment(date.endDate).format('YYYYMMDD') + '235959';
        this.dmService.getOption(null, this.REQUEST_URL,"?startDate="+startDate+"&endDate=" + endDate).subscribe(
            (res: HttpResponse<any>) => {
                this.source.localdata =this.customDate(res.body.RESULT);
                this.dataAdapter = new jqx.dataAdapter(this.source);
            },
            () => {
              console.error();
            }
          );
    }

    customDate(list: any[]): any[] {
        list.forEach(unitItem => {
            unitItem.ngayVao = unitItem.timeIn? DateUtil.formatDate(unitItem.timeIn):null;
            unitItem.ngayRa = unitItem.timeOut? DateUtil.formatDate(unitItem.timeOut):null;
            unitItem.userName = unitItem.acount? (unitItem.acount.fullName + '(' + unitItem.acount.userName + ')'):''
        });
        return list;
      }
    public onRowSelect(event:any):void{
        this.selectedEntity = event.args.row;
    }

    createData():void{

    }
    updateData():void{
        
    }
    deleteData():void{
        
    }

    reLoad():void{

    }
    
}
