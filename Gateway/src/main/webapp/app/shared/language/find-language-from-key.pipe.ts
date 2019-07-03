import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'findLanguageFromKey' })
export class FindLanguageFromKeyPipe implements PipeTransform {
  private languages: any = {
    fi: { name: 'Suomi' },
    fr: { name: 'Français' },
    gl: { name: 'Galego' },
    de: { name: 'Deutsch' },
    es: { name: 'Español' }
    // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
  };
  transform(lang: string): string {
    return this.languages[lang].name;
  }
}
