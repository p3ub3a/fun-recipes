import { Directive, ElementRef, HostBinding, HostListener } from "@angular/core";

@Directive({
    selector: '[appDropdown]'
})
export class DropdownDirective{
    @HostBinding('class.open') isToggled = false;

    @HostListener('document:click', ['$event']) toggleDropdown(event: Event){
        this.isToggled = this.elementRef.nativeElement.contains(event.target) ? !this.isToggled : false;
    }

    constructor(private elementRef: ElementRef) {}
}