package solutions.isky.gaurangarevolution.data.models;

import solutions.isky.gaurangarevolution.R;

public class OutGenericMessItem extends GenericMessItem {

    public OutGenericMessItem(MessModel messModel) {
        super(messModel);
    }
    @Override
    public int getType() {
        return R.id.fastadapter_out_generic_mess_item_id;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.out_mess;
    }
}
