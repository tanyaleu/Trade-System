import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestTs } from './request-ts.model';
import { RequestTsPopupService } from './request-ts-popup.service';
import { RequestTsService } from './request-ts.service';

@Component({
    selector: 'ts-request-ts-delete-dialog',
    templateUrl: './request-ts-delete-dialog.component.html'
})
export class RequestTsDeleteDialogComponent {

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

    confirmDelete(id: number) {
        this.requestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestListModification',
                content: 'Deleted an request'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'ts-request-ts-delete-popup',
    template: ''
})
export class RequestTsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestPopupService: RequestTsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestPopupService
                .open(RequestTsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
