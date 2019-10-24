class EditUserProfile {

    private static readonly NAME = "EditUserProfile";

    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * init() is called from an AJAX GET, and should populate the form if and 
     * only if the GET did not have an error
     */
    private static init() {
        if (!EditUserProfile.isInit) {
            $("body").append(Handlebars.templates[EditUserProfile.NAME + ".hb"]());
            $("#" + EditUserProfile.NAME + "-Update").click(EditUserProfile.editNameAndIntro);
            $("#" + EditUserProfile.NAME + "-Reset").click(EditUserProfile.resetPwd);
            $("#" + EditUserProfile.NAME + "-Cancel").click(EditUserProfile.hide);
            EditUserProfile.isInit = true;
        }
    }

    /**
     * Refresh() doesn't really have much meaning, but just like in sNavbar, we
     * have a refresh() method so that we don't have front-end code calling
     * init().
     */
    public static refresh() {
        EditUserProfile.init();
    }

    /**
     * Hide the EditUserProfile.  Be sure to clear its fields first
     */
    private static hide() {
        $("#" + EditUserProfile.NAME + "-username").val("");
        $("#" + EditUserProfile.NAME + "-intro").val("");
        $("#" + EditUserProfile.NAME).modal("hide");
    }

    /**
     * Show the EditUserProfile.  Be sure to clear its fields, because there are
     * ways of making a Bootstrap modal disapper without clicking Close, and
     * we haven't set up the hooks to clear the fields on the events associated
     * with those ways of making the modal disappear.
     */
    public static show(data: any) {
        $("#" + EditUserProfile.NAME + "-username").val(data.mData.uSername);
        console.log(data.mData.uSername);
        $("#" + EditUserProfile.NAME + "-intro").val(data.mData.uIntro);
        $("#" + EditUserProfile.NAME).modal("show");
    }


    /**
     * Send data to submit the form only if the fields are both valid.  
     * Immediately hide the form when we send data, so that the user knows that 
     * their click was received.
     */
    private static editNameAndIntro() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        let username = "" + $("#" + EditUserProfile.NAME + "-username").val();
        let intro = "" + $("#" + EditUserProfile.NAME + "-intro").val();
        if (username === "" || intro === "") {
            window.alert("Error: username or intro is not valid");
            return;
        }
        EditUserProfile.hide();
        $.ajax({
            type: "PUT",
            url: "/" + uid + "/userprofile",
            dataType: "json",
            data: JSON.stringify({ uUsername: username, uIntro: intro, uEmail: uemail, sessionKey: ukey }),
            //success: EditEntryForm.onSubmitResponse
        });
    }

    private static resetPwd() {
        let pwd = "" + $("#" + EditUserProfile.NAME + "-pwd").val();
        if (pwd === "") {
            window.alert("Error: password is not valid");
            return;
        }
        EditUserProfile.hide();
        $.ajax({
            type: "PUT",
            url: "/" + uid + "/updatepwd",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey ,uPassword: pwd}),
            //success: EditEntryForm.onSubmitResponse
        });
    }
}