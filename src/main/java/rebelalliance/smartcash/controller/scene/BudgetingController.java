package rebelalliance.smartcash.controller.scene;

import rebelalliance.smartcash.component.Navbar;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;

public class BudgetingController extends BaseController implements IController {
    @Override
    public void init() {
        this.addHeader();
    }
}
