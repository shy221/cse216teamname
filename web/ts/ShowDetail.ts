// a global for the ShowDetail of the program.  See newEntryForm for 
// explanation
//var ShowDetail: ShowDetail;

/**
 * ShowDetail encapsulates all of the code for the form for editing an entry
 */
class ShowDetail {

    private static readonly NAME = "ShowDetail";

    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * init() is called from an AJAX GET, and should populate the form if and 
     * only if the GET did not have an error
     */
    private static init() {
        if (!ShowDetail.isInit) {
            ShowDetail.isInit = true;
            $("body").append(Handlebars.templates[ShowDetail.NAME + ".hb"]());
            $("#" + ShowDetail.NAME + "-commentsbtn").click(ShowDetail.showComments);
            $("#" + ShowDetail.NAME + "-likebtn").click(ShowDetail.likePost);
            $("#" + ShowDetail.NAME + "-editbtn").click(ShowDetail.clickEdit);
            $("#" + ShowDetail.NAME + "-delbtn").click(ShowDetail.clickDelete);
            $("#" + ShowDetail.NAME + "-closebtn").click(ShowDetail.hide);
        }
    }

    private static reloadLike() {
        let id = "" + $("#" + ShowDetail.NAME + "-detailId").val();

        // Issue a GET, and then pass the result to update()
        $.ajax({
            type: "GET",
            url: "/messages/" + id,
            dataType: "json",
            success: ShowDetail.likeResponse
        });
    }

    private static likeResponse(data: any) {
        $("#" + ShowDetail.NAME + "-likebtn").text("");
        $("#" + ShowDetail.NAME + "-likebtn").text("Like: " + data.mData.mLikes);
    }

    /**
     * Refresh() doesn't really have much meaning, but just like in sNavbar, we
     * have a refresh() method so that we don't have front-end code calling
     * init().
     */
    public static refresh() {
        ShowDetail.init();
    }

    /**
     * update() is the private method used by refresh() to update the 
     * ShowDetails
     */
    private static update(data: any) {
        // Remove the data, if it exists
        $("#" + ShowDetail.NAME).remove();
        $("body").append(Handlebars.templates[ShowDetail.NAME + ".hb"](data));
        $("#" + ShowDetail.NAME + "-commentsbtn").click(ShowDetail.showComments);
        $("#" + ShowDetail.NAME + "-likebtn").click(ShowDetail.likePost);
        $("#" + ShowDetail.NAME + "-editbtn").click(ShowDetail.clickEdit);
        $("#" + ShowDetail.NAME + "-delbtn").click(ShowDetail.clickDelete);
        $("#" + ShowDetail.NAME + "-closebtn").click(ShowDetail.hide);
    }

    /**
     * Hide the ShowDetail.  Be sure to clear its fields first
     */
    private static hide() {
        $("#" + ShowDetail.NAME + "-title").val("");
        $("#" + ShowDetail.NAME + "-username").val("");
        $("#" + ShowDetail.NAME + "-message").val("");
        $("#" + ShowDetail.NAME + "-editId").val("");
        $("#" + ShowDetail.NAME + "-created").text("");
        $("#" + ShowDetail.NAME + "-likebtn").text("");
        $("#" + ShowDetail.NAME).modal("hide");
    }

    /**
     * Show the ShowDetail.  Be sure to clear its fields, because there are
     * ways of making a Bootstrap modal disapper without clicking Close, and
     * we haven't set up the hooks to clear the fields on the events associated
     * with those ways of making the modal disappear.
     */
    public static show(data: any) {
        $.ajax({
            type: "GET",
            url: "/messages/" + id + "/comments",
            dataType: "json",
            // 用ShowComments里的show
            success: ShowDetail.update
            //如果refresh 不行就改回listAllComments
        });
        $("#" + ShowDetail.NAME + "-title").text(data.mData.mTitle);
        //这个mName名字需要和backend确认
        $("#" + ShowDetail.NAME + "-username").text(data.mData.mName);
        $("#" + ShowDetail.NAME + "-message").val(data.mData.mContent);
        $("#" + ShowDetail.NAME + "-detailId").val(data.mData.mId);
        $("#" + ShowDetail.NAME + "-created").text(data.mData.mCreated);
        $("#" + ShowDetail.NAME + "-likebtn").text("Like: " + data.mData.mLikes);
        $("#" + ShowDetail.NAME).modal("show");
    }


    /**
     * Send data to submit the form only if the fields are both valid.  
     * Immediately hide the form when we send data, so that the user knows that 
     * their click was received.
     */
    private static likePost() {
        // get the values of the id of the current post
        let id = "" + $("#" + ShowDetail.NAME + "-detailId").val();

        // set up an AJAX post.  When the server replies, the result will go to
        // onSubmitResponse
        $.ajax({
            type: "PUT",
            url: "/messages/" + id + "/likes",
            dataType: "json",
            success: ShowDetail.reloadLike
        });
    }

    /**
     * 显示与message对应的所有comments
     */
    private static showComments() {
        let id = "" + $("#" + ShowDetail.NAME + "-detailId").val();
        ShowDetail.hide();
        $.ajax({
            type: "GET",
            url: "/messages/" + id + "/comments",
            dataType: "json",
            // 用ShowComments里的show
            success: ShowDetail.listAllComments
            //如果refresh 不行就改回listAllComments
        });
    }

    private static listAllComments(data: any) {
        //页面清空
        $("#" + ShowDetail.NAME).remove();
        //带入数据再load一边
        $("body").append(Handlebars.templates[ShowDetail.NAME + ".hb"](data));

    }


    /**
     * clickDelete is the code we run in response to a click of a delete button
     */
    private static clickDelete() {
        // for now, just print the ID that goes along with the data in the row
        // whose "delete" button was clicked
        let id = "" + $("#" + ShowDetail.NAME + "-detailId").val();

        ShowDetail.hide();
        $.ajax({
            type: "DELETE",
            url: "/messages/" + id,
            dataType: "json",
            // TODO: we should really have a function that looks at the return
            //       value and possibly prints an error message.
            success: ElementList.refresh
        });
    }

    /**
     * clickEdit is the code we run in response to a click of a delete button
     */
    private static clickEdit() {
        // as in clickDelete, we need the ID of the row
        let id = "" + $("#" + ShowDetail.NAME + "-detailId").val();

        ShowDetail.hide();
        $.ajax({
            type: "GET",
            url: "/messages/" + id,
            dataType: "json",
            success: EditEntryForm.show
        });
    }
} // end class ShowDetails