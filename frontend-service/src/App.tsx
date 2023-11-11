import "./App.css";
import { ThemeProvider } from "./components/theme-provider";
import { MenubarFront } from "./components/MenubarFront";
import { SearchButton } from "./components/SearchButton";

function App() {
	return (
		<ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
			<MenubarFront />
			<SearchButton />
		</ThemeProvider>
	);
}

export default App;
