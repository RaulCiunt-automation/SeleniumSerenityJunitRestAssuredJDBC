package steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import pages.AbstractPage;

public abstract class AbstractSteps<T extends AbstractPage> extends ScenarioSteps {

    private static final long serialVersionUID = 1L;

    private Class<T> pageType;

    public AbstractSteps(final Pages pages, java.lang.Class pageType) {
        super(pages);
        this.pageType = pageType;
    }

    protected T currentPage() {
        return getPages().currentPageAt(pageType);
    }

    @Step("Refresh the current page")
    public void refreshPage(){currentPage().refreshPage();}

    @Step("Refresh the current page with clear cache(only on firefox)")
    public void refreshPageClearCache(){currentPage().refreshPageClearCache();}
}
