import "./admin-logs.css";
import AdminService from '../../../../../service/AdminService';
import AutenticationService from "../../../../../service/AutenticationService";
import { useState, useEffect } from 'react';
import AdminLogsSidebar from "./AdminLogsSidebar/AdminLogsSidebar";


export default function AdminLogs() {


  const [logs, setLogs] = useState([{
    id: '',
    action: '',
    date: '',
    time: '',
    entity: '',
    entityId: '',
    performer: '',
    performerData: '',
    newData: '',
    oldData: '',
  }])

  const updateLogs = () => {
    AdminService.getLogs().then(
      response => {
        setLogs(response.data);
      },
      (error) => {
        try {
          if (error.response.data.string) {
            setLogs([{ id: "" }])
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
    )
  }


  useEffect(() => {
    updateLogs()

  }, [])

  const getLogs = (handleChange) => {
    if (logs[0].id === '')
      return
    return (
      logs.map((interval, index) => {
        return (
          <tr key={index}>

            <td className="AdminLogs-td" title={interval.id}>{interval.id}</td>
            <td className="AdminLogs-td" title={interval.action}>{interval.action}</td>
            <td className="AdminLogs-td" title={interval.date}>{interval.date}</td>
            <td className="AdminLogs-td" title={interval.time}>{interval.time}</td>
            <td className="AdminLogs-td" title={interval.entity}>{interval.entity}</td>
            <td className="AdminLogs-td" title={interval.entityId}>{interval.entityId}</td>
            <td className="AdminLogs-td" title={interval.performer}>{interval.performer}</td>
            <td className="AdminLogs-td" title={interval.performerData}>{interval.performerData}</td>
            <td className="AdminLogs-td" title={interval.newData}>{interval.newData}</td>
            <td className="AdminLogs-td" title={interval.oldData}>{interval.oldData}</td>

          </tr>
        )
      })
    )
  }
  return (
    <div className="AdminLogs">
      <AdminLogsSidebar updateLogs={updateLogs} />

      <table className="table" id="AdminLogs-table">
        <tbody>
          <tr>

            <th width="10%" className="AdminLogs-table-th" title="ID">ID</th>
            <th width="10%" className="AdminLogs-table-th" title="Action">Action</th>
            <th width="10%" className="AdminLogs-table-th" title="Date">Date</th>
            <th width="10%" className="AdminLogs-table-th" title="Time">Time</th>
            <th width="10%" className="AdminLogs-table-th" title="Entity">Entity</th>
            <th width="10%" className="AdminLogs-table-th" title="Entity ID">Entity ID</th>
            <th width="10%" className="AdminLogs-table-th" title="Performer">Performer</th>
            <th width="10%" className="AdminLogs-table-th" title="Performer Data">Performer Data</th>
            <th width="10%" className="AdminLogs-table-th" title="New Data">New Data</th>
            <th width="10%" className="AdminLogs-table-th" title="Old Data">Old Data</th>
          </tr>
          {getLogs()}
        </tbody>
      </table>


    </div>
  );
}
