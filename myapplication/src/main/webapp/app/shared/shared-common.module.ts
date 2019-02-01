import { NgModule } from '@angular/core';

import { MyappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MyappSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MyappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MyappSharedCommonModule {}
