import { Route } from '@angular/router';

import { TsMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'ts-metrics',
    component: TsMetricsMonitoringComponent,
    data: {
        pageTitle: 'Application Metrics'
    }
};
