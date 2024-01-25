import Zoos from '../components/Zoos';
import { useNavigate } from 'react-router-dom';

export default function ZoosPage(){
const navigate = useNavigate();

return(
<div className="App">
    <header className="App-header">
    <Zoos></Zoos>
    <p></p>
    <button onClick={()=>navigate("/")}>Home</button>
    </header>
</div>
);
}