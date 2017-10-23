import { BaseEntity } from './../../shared';

export class BusinessCapability implements BaseEntity {
    constructor(
        public id?: number,
        public commonName?: string,
        public displayName?: string,
        public description?: string,
        public sortOrder?: number,
        public dateAdded?: any,
        public dateModified?: any,
        public parent?: BaseEntity,
        public displayNames?: BaseEntity[],
    ) {
    }
}
