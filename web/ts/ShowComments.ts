class ShowComments {
    private static readonly NAME = "ShowComments";
    private static isInit = false;

    private static init() {
        if (!ShowComments.isInit) {
            $("body").append(Handlebars.templates[ShowComments.NAME + ".hb"]());
            $("#" + ShowComments.NAME + "-Close").click(ShowComments.hide);
            ShowComments.isInit = true;
        }
    }
    public static refresh() {
        ShowComments.init();
    }
    private static hide() {
        $('.modal-backdrop').remove();
        $("#" + ShowComments.NAME).remove();
        $("#" + ShowComments.NAME).modal("hide");
    }
    public static show(data: any) {
        $("#" + ShowComments.NAME).remove();
        // Use a template to re-generate the table, and then insert it
        $("body").append(Handlebars.templates[ShowComments.NAME + ".hb"](data));
        $("." + ShowComments.NAME + "-user").click(ShowComments.user);
        //$("." + ShowComments.NAME + "-user").text(data.mData.cUsername);
        $("#" + ShowComments.NAME + "-Close").click(ShowComments.hide);
        $("#" + ShowComments.NAME).modal("show");
    }

    public static user(){
        let uid = $(this).data("value");
        $.ajax({
            type: "POST",
            url: "/" + uid + "/userprofile",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: UserProfile.showBasic
        });
        $.ajax({
            type: "POST",
            url: "/" + uid + "/userposts",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: ShowPosts.show
        });
    }
}