import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TsRequestTsModule } from './request-ts/request-ts.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TsRequestTsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TsEntityModule {}
