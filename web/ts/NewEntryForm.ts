/**
 * NewEntryForm encapsulates all of the code for the form for adding an entry
 */
class NewEntryForm {

    /**
     * The name of the DOM entry associated with NewEntryForm
     */
    private static readonly NAME = "NewEntryForm";

    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * Initialize the NewEntryForm by creating its element in the DOM and 
     * configuring its buttons.  This needs to be called from any public static 
     * method, to ensure that the Singleton is initialized before use
     */
    private static init() {
        if (!NewEntryForm.isInit) {
            $("body").append(Handlebars.templates[NewEntryForm.NAME + ".hb"]());
            $("#" + NewEntryForm.NAME + "-OK").click(NewEntryForm.submitForm);
            $("#" + NewEntryForm.NAME + "-Close").click(NewEntryForm.hide);
            NewEntryForm.isInit = true;
        }
    }

    /**
     * Refresh() doesn't really have much meaning, but just like in sNavbar, we
     * have a refresh() method so that we don't have front-end code calling
     * init().
     */
    public static refresh() {
        NewEntryForm.init();
    }

    /**
     * Hide the NewEntryForm.  Be sure to clear its fields first
     */
    private static hide() {
        $("#" + NewEntryForm.NAME + "-title").val("");
        $("#" + NewEntryForm.NAME + "-message").val("");
        // $("#" + NewEntryForm.NAME + "-link").val("");
        $("#" + NewEntryForm.NAME + "-attachment").val("");
        $("#" + NewEntryForm.NAME).modal("hide");
    }

    /**
     * Show the NewEntryForm.  Be sure to clear its fields, because there are
     * ways of making a Bootstrap modal disapper without clicking Close, and
     * we haven't set up the hooks to clear the fields on the events associated
     * with those ways of making the modal disappear.
     */
    public static show() {
        $("#" + NewEntryForm.NAME + "-title").val("");
        $("#" + NewEntryForm.NAME + "-message").val("");
        $("#" + NewEntryForm.NAME + "-link").val("");
        $("#" + NewEntryForm.NAME + "-attachment").val("");
        $("#" + NewEntryForm.NAME).modal("show");
    }


    /**
     * Send data to submit the form only if the fields are both valid.  
     * Immediately hide the form when we send data, so that the user knows that 
     * their click was received.
     */
    private static submitForm() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        let title = "" + $("#" + NewEntryForm.NAME + "-title").val();
        let msg = "" + $("#" + NewEntryForm.NAME + "-message").val();
        let link = "" + $("#" + NewEntryForm.NAME + "-link").val();
        // let att = "" + $("#" + NewEntryForm.NAME + "-attachment").val();
        let att = "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";

        /*
        var myReader:FileReader = new FileReader();
        let att = myReader.readAsDataURL(document.querySelector('input[type=file]').files[0]);
        */

        if (title === "" || msg === "") {
            window.alert("Error: title or message is not valid");
            return;
        }
        NewEntryForm.hide();

        console.log(att);

        // set up an AJAX post.  When the server replies, the result will go to
        // onSubmitResponse
        $.ajax({
            type: "POST",
            url: "/messages",
            dataType: "json",
            data: JSON.stringify({ mTitle: title, mMessage: msg, mLink: link, fileData: att, mime: "application/pdf", uid: uid, uEmail: uemail, sessionKey: ukey }),
            success: NewEntryForm.onSubmitResponse
        });
    }

    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a 
     * result.
     * 
     * @param data The object returned by the server
     */
    private static onSubmitResponse(data: any) {
        // If we get an "ok" message, clear the form and refresh the main 
        // listing of messages
        if (data.mStatus === "ok") {
            ElementList.refresh();
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "error") {
            window.alert("The server replied with an error:\n" + data.mMessage);
        }
        // Handle other errors with a less-detailed popup message
        else {
            window.alert("Unspecified error");
        }
    }
}