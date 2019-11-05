/**
 * Login encapsulates all of the code for the form for user login
 */
class Login {
    private static readonly NAME = "Login";
    private static isInit = false;

    private static init() {
        if (!Login.isInit) {
            $("body").append(Handlebars.templates[Login.NAME + ".hb"]());
            $("#" + Login.NAME + "-Login").click(Login.submitForm);
            $("#" + Login.NAME + "-Close").click(Login.hide);
            Login.isInit = true;
        }
    }
    public static refresh() {
        Login.init();
    }
    private static hide() {
        $("#" + Login.NAME + "-ID").val("");
        $("#" + Login.NAME + "-pwd").val("");
        $("#" + Login.NAME).modal("hide");
        Navbar.refresh();
    }
    public static show() {
        $("#" + Login.NAME + "-ID").val("");
        $("#" + Login.NAME + "-pwd").val("");
        $("#" + Login.NAME).modal("show");
    }
    private static submitForm() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        // not sure about the backend return type
        let ID = "" + $("#" + Login.NAME + "-ID").val();
        let pwd = "" + $("#" + Login.NAME + "-pwd").val();
        if (ID === "" || pwd === "") {
            window.alert("Error: ID or pwd is not valid");
            return;
        }
        Login.hide();
        // set up an AJAX post.  When the server replies, the result will go to
        // onSubmitResponse
        $.ajax({
            type: "POST",
            //not sure abour url
            url: "/login",
            dataType: "json",
            data: JSON.stringify({ "uEmail": ID, "uPassword": pwd }),
            success: Login.onSubmitResponse
        });
    }
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a 
     * result.
     * 
     * @param data The object returned by the server
     */
    private static onSubmitResponse(data: any) {
        // If we get an "ok" pwd, clear the form and refresh the main 
        // listing of pwds
        // Initialization:
        // Add `auth2` module
        /*gapi.load('auth2', function() {
        // Initialize `auth2`
        gapi.auth2.init().then(function(auth2) {
            
          // If the user is already signed in
          if (auth2.isSignedIn.get()) {
            var googleUser = auth2.currentUser.get();
            
            // Change user's profile information
            changeProfile(googleUser);
          }
        });
      });*/
        if (data.mStatus === "ok") {
            loginState = true;
            uid = data.mData.uId;
            ukey = data.mData.sessionKey;
            uemail = data.mData.uEmail;
            Navbar.refresh();
            NewEntryForm.refresh();
            ElementList.refresh();
            EditEntryForm.refresh();
            ShowDetail.refresh();
        }
        // Handle explicit errors with a detailed popup pwd
        else if (data.mStatus === "error") {
            window.alert("The server replied with an error:\n" + data.mpwd);
        }
        // Handle other errors with a less-detailed popup pwd
        else {
            window.alert("Unspecified error");
        }
    }
}