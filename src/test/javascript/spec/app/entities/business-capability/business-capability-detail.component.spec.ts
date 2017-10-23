/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GovernanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BusinessCapabilityDetailComponent } from '../../../../../../main/webapp/app/entities/business-capability/business-capability-detail.component';
import { BusinessCapabilityService } from '../../../../../../main/webapp/app/entities/business-capability/business-capability.service';
import { BusinessCapability } from '../../../../../../main/webapp/app/entities/business-capability/business-capability.model';

describe('Component Tests', () => {

    describe('BusinessCapability Management Detail Component', () => {
        let comp: BusinessCapabilityDetailComponent;
        let fixture: ComponentFixture<BusinessCapabilityDetailComponent>;
        let service: BusinessCapabilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GovernanceTestModule],
                declarations: [BusinessCapabilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BusinessCapabilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(BusinessCapabilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BusinessCapabilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BusinessCapabilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BusinessCapability(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.businessCapability).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
