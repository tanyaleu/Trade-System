import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestTs } from './request-ts.model';
import { RequestTsPopupService } from './request-ts-popup.service';
import { RequestTsService } from './request-ts.service';

@Component({
    selector: 'ts-request-ts-process-dialog',
    templateUrl: './request-ts-process-dialog.component.html'
})
export class RequestTsProcessDialogComponent {

    request: RequestTs;

    constructor(
        private requestService: RequestTsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmProcess(id: number) {
        this.requestService.process().subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestListModification',
                content: 'Process requests'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'ts-request-ts-process-popup',
    template: ''
})
export class RequestTsProcessPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestPopupService: RequestTsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestPopupService
                .open(RequestTsProcessDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
