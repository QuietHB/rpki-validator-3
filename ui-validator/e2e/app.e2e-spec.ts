import { AppPage } from './app.po';

describe('ui-validator App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Quick Overview of BGP Origin Validation');
  });
});