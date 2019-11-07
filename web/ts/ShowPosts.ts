class ShowPosts {
    private static readonly NAME = "ShowPosts";
    private static isInit = false;

    private static init() {
        if (!ShowPosts.isInit) {
            $("body").append(Handlebars.templates[ShowPosts.NAME + ".hb"]());
            $("#" + ShowPosts.NAME + "-Close").click(ShowPosts.hide);
            ShowPosts.isInit = true;
        }
    }
    public static refresh() {
        ShowPosts.init();
        /*$.ajax({
            type: "POST",
            url: "/" + uid + "/userposts",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: ShowPosts.show
        });*/
    }
    private static hide() {
        $('.modal-backdrop').remove();
        $("#" + ShowPosts.NAME).remove();
        $("#" + ShowPosts.NAME).modal("hide");
    }
    public static show(data: any) {
        $("#" +ShowPosts.NAME).remove();
        // Use a template to re-generate the table, and then insert it
        $("body").append(Handlebars.templates[ShowPosts.NAME + ".hb"](data));
        
        //$("." + ShowPosts.NAME + "-user").click(ShowPosts.user);
        //$("." + ShowComments.NAME + "-user").text(data.mData.cUsername);
        $("#" + ShowPosts.NAME + "-Close").click(ShowPosts.hide);
        $("#" + ShowPosts.NAME).modal("show");
    }

   
}