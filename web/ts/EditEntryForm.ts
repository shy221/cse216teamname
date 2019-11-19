// a global for the EditEntryForm of the program.  See newEntryForm for 
// explanation
//var editEntryForm: EditEntryForm;

/**
 * EditEntryForm encapsulates all of the code for the form for editing an entry
 */
class EditEntryForm {

    private static readonly NAME = "EditEntryForm";

    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * init() is called from an AJAX GET, and should populate the form if and 
     * only if the GET did not have an error
     */
    private static init() {
        if (!EditEntryForm.isInit) {
            $("body").append(Handlebars.templates[EditEntryForm.NAME + ".hb"]());
            $("#" + EditEntryForm.NAME + "-Update").click(EditEntryForm.submitForm);
            $("#" + EditEntryForm.NAME + "-Cancel").click(EditEntryForm.hide);
            EditEntryForm.isInit = true;
        }
    }

    /**
     * Refresh() doesn't really have much meaning, but just like in sNavbar, we
     * have a refresh() method so that we don't have front-end code calling
     * init().
     */
    public static refresh() {
        EditEntryForm.init();
    }

    /**
     * Hide the EditEntryForm.  Be sure to clear its fields first
     */
    private static hide() {
        $("#" + EditEntryForm.NAME + "-title").val("");
        $("#" + EditEntryForm.NAME + "-message").val("");
        $("#" + EditEntryForm.NAME + "-link").val("");
        $("#" + EditEntryForm.NAME + "-attachment").val("");
        $("#" + EditEntryForm.NAME + "-editId").val("");
        $("#" + EditEntryForm.NAME + "-created").text("");
        $("#" + EditEntryForm.NAME).modal("hide");
    }

    /**
     * Show the EditEntryForm.  Be sure to clear its fields, because there are
     * ways of making a Bootstrap modal disapper without clicking Close, and
     * we haven't set up the hooks to clear the fields on the events associated
     * with those ways of making the modal disappear.
     */
    public static show(data: any) {
        $("#" + EditEntryForm.NAME + "-title").val(data.mData.mTitle);
        console.log(data.mData.mTitle);
        $("#" + EditEntryForm.NAME + "-message").val(data.mData.mContent);
        $("#" + EditEntryForm.NAME + "-link").val(data.mData.mLink);
        $("#" + EditEntryForm.NAME + "-attachment").val("");
        $("#" + EditEntryForm.NAME + "-editId").val(data.mData.mId);
        $("#" + EditEntryForm.NAME + "-created").text(data.mData.mCreated);
        $("#" + EditEntryForm.NAME).modal("show");
    }


    /**
     * Send data to submit the form only if the fields are both valid.  
     * Immediately hide the form when we send data, so that the user knows that 
     * their click was received.
     */
    private static submitForm() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        let title = "" + $("#" + EditEntryForm.NAME + "-title").val();
        let msg = "" + $("#" + EditEntryForm.NAME + "-message").val();
        let link = "" + $("#" + EditEntryForm.NAME + "-link").val();
        let file = $("#" + EditEntryForm.NAME + "-attachment").prop('files')[0];

        let id = "" + $("#" + EditEntryForm.NAME + "-editId").val();
        if (title === "" || msg === "") {
            window.alert("Error: title or message is not valid");
            return;
        }
        EditEntryForm.hide();

        console.log(link);
        console.log(file);

        var myReader:FileReader = new FileReader();
        myReader.onload = function(completionEvent: any) {
            // wait till reader finished reading
            var att = btoa(completionEvent.target.result);
            console.log(att);

            // set up an AJAX post.  When the server replies, the result will go to
            // onSubmitResponse
            $.ajax({
                type: "PUT",
                url: "/messages/" + id,
                dataType: "json",
                data: JSON.stringify({ mTitle: title, mMessage: msg, mLink: link, fileData: att, mime: "application/pdf", uEmail: uemail, sessionKey: ukey}),
                success: EditEntryForm.onSubmitResponse
            });
        }
        myReader.readAsBinaryString(file);
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
} // end class EditEntryForms