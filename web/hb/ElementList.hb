<div class="panel panel-default" id="ElementList">
    <div class="panel-heading">
        <h3 class="panel-title">All Data</h3>
    </div>
    <table class="table">
        <tbody>
            {{#each mData}}
            <tr>
                <td>{{this.mTitle}}</td>
                <td><button class="ElementList-detailbtn" data-value="{{this.mId}}">Detail</button></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>