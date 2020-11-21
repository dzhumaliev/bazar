package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import solutions.isky.gaurangarevolution.presentation.ui.login.fragments.LoginFragment;

public class LoginNavigations {
    private static final String TAG = "LoginNavigations";


    public static void showLoginpage(final Context context, final FragmentManager fragmentManager, final int container, final boolean backStack) {

        if ((fragmentManager.findFragmentByTag("login") != null) && (fragmentManager.findFragmentByTag("login").isVisible()))

            return;
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, loginFragment, "login");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        if (backStack)
            fragmentTransaction.addToBackStack("login");
        fragmentTransaction.commit();

    }

}
