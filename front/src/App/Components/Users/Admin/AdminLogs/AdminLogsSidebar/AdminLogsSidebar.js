import '../../../../../../styles/sidebar.css';
import AdminService from '../../../../../../service/AdminService';
import AutenticationService from '../../../../../../service/AutenticationService';

export default function AdminLogsSidebar(props) {

    const clearLogs = () => {

        AdminService.clearLogs().then(
            (response) => {
                alert("Logs cleared Successfully.");
                props.updateLogs()
            },
            (error) => {
                try {
                    if (error.response.data.string) {
                        alert(error.response.data.string);
                        return;
                    }
                    if (error.response) {
                        alert("Login expired, please login again.");
                        AutenticationService.logOut();
                    }
                } catch {
                    alert("Servers are down, please try again later.");
                    AutenticationService.logOut();
                }
            }
        );
    }



    return (
        <>
            <div className="sidebar-top"></div>
            <div className="side-bar">

                <div className="side-bar-div"><button type="button" className="side-bar-button" title="Adminlogs" onClick={clearLogs}>Clear </button></div>

            </div>
        </>
    )

}

