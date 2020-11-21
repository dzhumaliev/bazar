package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.DinParamsAddAdListener;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.FlagChoice;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.MultipleChoice;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.RangeChoice;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.SingleChoice;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.TextFildInput;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.YesNoChoice;

public class DinParamsAddAdLayout extends LinearLayout {

  DinParamsAddAdListener dinParamsAddAdListener;
  HashMap<Integer, ArrayList<HashMap<Integer, Object>>> dynamicParamses;

  public DinParamsAddAdListener onClickListener(DinParamsAddAdListener dinParamsAddAdListener) {
    this.dinParamsAddAdListener = dinParamsAddAdListener;
    return dinParamsAddAdListener;

  }

  public DinParamsAddAdLayout(Context context) {
    super(context);
  }

  public DinParamsAddAdLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DinParamsAddAdLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setUpViews(Context context, List<DinProps> dinPropses) {
    setUpViews(context, dinPropses, null);
  }

  public void setUpViews(Context context, List<DinProps> dinPropses, List<CategoryProp> catProps) {
    if (dinPropses != null && dinPropses.size() > 0) {
      setVisibility(VISIBLE);
      for (DinProps props : dinPropses) {
        switch (props.getType()) {
          case 9:
            setViewType9(context, props, false, catProps);
            break;
          case 8:
          case 6:
            setViewType8(context, props, false, catProps);
            break;
          case 4:
            setViewType4(context, props, false, catProps);
            break;
          case 5:
            setViewType5(context, props, false, catProps);
            break;
          case 2:
          case 1:
            setViewType1(context, props, InputType.TYPE_CLASS_TEXT, false, catProps);
            break;
          case 10:
            setViewType1(context, props, InputType.TYPE_CLASS_NUMBER, false, catProps);
            break;
          case 11:
            setViewType11(context, props, false, catProps);
            break;
        }
      }
    }
  }

  private void setViewType9(Context context, DinProps props, boolean necessary_to_fill,
      List<CategoryProp> catProps) {
    MultipleChoice multipleChoice = (MultipleChoice) LayoutInflater.from(context)
        .inflate(R.layout.multi_choise, this, false);
    multipleChoice.setData(context, props, necessary_to_fill, catProps);
    addView(multipleChoice);
  }

  private void setViewType8(Context context, DinProps props, boolean necessary_to_fill,
      List<CategoryProp> catProps) {
    SingleChoice singleChoice = (SingleChoice) LayoutInflater.from(context)
        .inflate(R.layout.single_choise, this, false);
    singleChoice.setData(context, props, necessary_to_fill, catProps);
    addView(singleChoice);
  }

  private void setViewType4(Context context, DinProps props, boolean necessary_to_fill,
      List<CategoryProp> catProps) {
    YesNoChoice yesNoChoice = (YesNoChoice) LayoutInflater.from(context)
        .inflate(R.layout.yes_no_choise, this, false);
    yesNoChoice.setData(context, props, necessary_to_fill, catProps);
    addView(yesNoChoice);
  }

  private void setViewType5(Context context, DinProps props, boolean necessary_to_fill,
      List<CategoryProp> catProps) {
    FlagChoice flagChoice = (FlagChoice) LayoutInflater.from(context)
        .inflate(R.layout.flag_choise, this, false);
    flagChoice.setData(context, props, necessary_to_fill, catProps);
    addView(flagChoice);
  }

  private void setViewType1(Context context, DinProps props, int type, boolean necessary_to_fill,
      List<CategoryProp> catProps) {
    TextFildInput textFildInput = (TextFildInput) LayoutInflater.from(context)
        .inflate(R.layout.text_fild, this, false);
    textFildInput.setData(context, props, type, necessary_to_fill, catProps);
    addView(textFildInput);
  }

  private void setViewType11(Context context, DinProps props, boolean necessary_to_fill,
      List<CategoryProp> catProps) {
    RangeChoice singleChoice = (RangeChoice) LayoutInflater.from(context)
        .inflate(R.layout.range_choise, this, false);
    singleChoice.setData(context, props, necessary_to_fill, catProps);
    addView(singleChoice);
  }

  public boolean collection_of_dynparams(boolean isError) {
    int size = getChildCount();
    dynamicParamses = new HashMap<>();
    for (int i = 0; i < size; i++) {
      DinProps dinProps = ((DinProps) getChildAt(i).getTag());
      if (dinProps.getType() == 9) {
        if (((MultipleChoice) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((MultipleChoice) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((MultipleChoice) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((MultipleChoice) getChildAt(i)).getIdCateg(),
                ((MultipleChoice) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((MultipleChoice) getChildAt(i)).getIdCateg(),
                ((MultipleChoice) getChildAt(i)).getParams(new ArrayList<>()));
          }

        } else {
          if (dinProps.getReq() == 1) {
            ((MultipleChoice) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          } else {
            if (dynamicParamses.containsKey(((MultipleChoice) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((MultipleChoice) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((MultipleChoice) getChildAt(i)).getIdCateg(),
                  ((MultipleChoice) getChildAt(i)).getParamsEmpty(maps));
            } else {
              dynamicParamses.put(((MultipleChoice) getChildAt(i)).getIdCateg(),
                  ((MultipleChoice) getChildAt(i)).getParamsEmpty(new ArrayList<>()));
            }
          }
        }
      } else if (dinProps.getType() == 8 || dinProps.getType() == 6) {
        if (((SingleChoice) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((SingleChoice) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((SingleChoice) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((SingleChoice) getChildAt(i)).getIdCateg(),
                ((SingleChoice) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((SingleChoice) getChildAt(i)).getIdCateg(),
                ((SingleChoice) getChildAt(i)).getParams(new ArrayList<>()));
          }
        } else {
          if (dinProps.getReq() == 1) {
            ((SingleChoice) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          }else{
            if (dynamicParamses.containsKey(((SingleChoice) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((SingleChoice) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((SingleChoice) getChildAt(i)).getIdCateg(),
                  ((SingleChoice) getChildAt(i)).getParamsEmty(maps));
            } else {
              dynamicParamses.put(((SingleChoice) getChildAt(i)).getIdCateg(),
                  ((SingleChoice) getChildAt(i)).getParamsEmty(new ArrayList<>()));
            }
          }
        }
      } else if (dinProps.getType() == 4) {
        if (((YesNoChoice) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((YesNoChoice) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((YesNoChoice) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((YesNoChoice) getChildAt(i)).getIdCateg(),
                ((YesNoChoice) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((YesNoChoice) getChildAt(i)).getIdCateg(),
                ((YesNoChoice) getChildAt(i)).getParams(new ArrayList<>()));
          }
        } else {
          if (dinProps.getReq() == 1) {
            ((YesNoChoice) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          }else{
            if (dynamicParamses.containsKey(((YesNoChoice) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((YesNoChoice) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((YesNoChoice) getChildAt(i)).getIdCateg(),
                  ((YesNoChoice) getChildAt(i)).getParamsEmty(maps));
            } else {
              dynamicParamses.put(((YesNoChoice) getChildAt(i)).getIdCateg(),
                  ((YesNoChoice) getChildAt(i)).getParamsEmty(new ArrayList<>()));
            }
          }
        }
      } else if (dinProps.getType() == 5) {
        if (((FlagChoice) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((FlagChoice) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((FlagChoice) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((FlagChoice) getChildAt(i)).getIdCateg(),
                ((FlagChoice) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((FlagChoice) getChildAt(i)).getIdCateg(),
                ((FlagChoice) getChildAt(i)).getParams(new ArrayList<>()));
          }
        } else {
          if (dinProps.getReq() == 1) {
            ((FlagChoice) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          }else{
            if (dynamicParamses.containsKey(((FlagChoice) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((FlagChoice) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((FlagChoice) getChildAt(i)).getIdCateg(),
                  ((FlagChoice) getChildAt(i)).getParamsEmpty(maps));
            } else {
              dynamicParamses.put(((FlagChoice) getChildAt(i)).getIdCateg(),
                  ((FlagChoice) getChildAt(i)).getParamsEmpty(new ArrayList<>()));
            }
          }
        }
      } else if (dinProps.getType() == 2 || dinProps.getType() == 1) {
        if (((TextFildInput) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((TextFildInput) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((TextFildInput) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                ((TextFildInput) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                ((TextFildInput) getChildAt(i)).getParams(new ArrayList<>()));
          }
        } else {
          if (dinProps.getReq() == 1) {
            ((TextFildInput) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          }else{
            if (dynamicParamses.containsKey(((TextFildInput) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((TextFildInput) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                  ((TextFildInput) getChildAt(i)).getParamsEmty(maps));
            } else {
              dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                  ((TextFildInput) getChildAt(i)).getParamsEmty(new ArrayList<>()));
            }
          }
        }
      } else if (dinProps.getType() == 10) {
        if (((TextFildInput) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((TextFildInput) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((TextFildInput) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                ((TextFildInput) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                ((TextFildInput) getChildAt(i)).getParams(new ArrayList<>()));
          }
        } else {
          if (dinProps.getReq() == 1) {
            ((TextFildInput) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          }else{
            if (dynamicParamses.containsKey(((TextFildInput) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((TextFildInput) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                  ((TextFildInput) getChildAt(i)).getParamsEmty(maps));
            } else {
              dynamicParamses.put(((TextFildInput) getChildAt(i)).getIdCateg(),
                  ((TextFildInput) getChildAt(i)).getParamsEmty(new ArrayList<>()));
            }
          }
        }
      } else if (dinProps.getType() == 11) {
        if (((RangeChoice) getChildAt(i)).isCheack()) {
          if (dynamicParamses.containsKey(((RangeChoice) getChildAt(i)).getIdCateg())) {
            ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                .get(((RangeChoice) getChildAt(i)).getIdCateg());
            dynamicParamses.put(((RangeChoice) getChildAt(i)).getIdCateg(),
                ((RangeChoice) getChildAt(i)).getParams(maps));
          } else {
            dynamicParamses.put(((RangeChoice) getChildAt(i)).getIdCateg(),
                ((RangeChoice) getChildAt(i)).getParams(new ArrayList<>()));
          }
        } else {
          if (dinProps.getReq() == 1) {
            ((RangeChoice) getChildAt(i))
                .setError(getResources().getString(R.string.error_emty_text));
            isError = true;
          }else{
            if (dynamicParamses.containsKey(((RangeChoice) getChildAt(i)).getIdCateg())) {
              ArrayList<HashMap<Integer, Object>> maps = dynamicParamses
                  .get(((RangeChoice) getChildAt(i)).getIdCateg());
              dynamicParamses.put(((RangeChoice) getChildAt(i)).getIdCateg(),
                  ((RangeChoice) getChildAt(i)).getParamsEmpty(maps));
            } else {
              dynamicParamses.put(((RangeChoice) getChildAt(i)).getIdCateg(),
                  ((RangeChoice) getChildAt(i)).getParamsEmpty(new ArrayList<>()));
            }
          }
        }
      }
    }
    JsonObject elements = (JsonObject) new Gson().toJsonTree(dynamicParamses);
    Log.v("elements", elements.toString());
    return isError;
  }

  public JsonObject getDyn() {
    return (JsonObject) new Gson().toJsonTree(dynamicParamses);
  }

}
